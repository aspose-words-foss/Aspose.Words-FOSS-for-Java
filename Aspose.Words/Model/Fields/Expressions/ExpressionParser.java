// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/11/2006 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.java.collections.StringSwitchMap;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.Debug;
import com.aspose.FormatterPal;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.msChar;


/// <summary>
/// Parses an expression to a queue of execution items.
/// </summary>
class ExpressionParser
{
    private ExpressionParser(FieldContext fieldContext, IExpressionParserBehavior builder)
    {
        mFieldContext = fieldContext;
        mBuilder = builder;
    }

    /// <summary>
    /// Parses an expression to a queue of execution items.
    /// </summary>
    /// <returns>A queue of execution items or null if an error has been encountered.</returns>
    static ExecutionQueue parse(FieldContext fieldContext, String expression, IExpressionParserBehavior builder) throws Exception
    {
        ExpressionParser parser = new ExpressionParser(fieldContext, builder);
        return parser.parse(expression);
    }

    private ExecutionQueue parse(String exp) throws Exception
    {
        ArrayList<Object> expression = parseExpression(exp);
        if (expression == null)
            return null;

        expression = mBuilder.normalizeExpression(expression, mFieldContext);
        if (expression == null)
            return null;

        expression = joinSequencedUnaryMinusOperators(expression);

        ExecutionQueue queue = buildExecutionQueue(expression);
        if (queue == null)
            return null;

        return mBuilder.normalizeExecutionQueue(queue, mFieldContext, exp);
    }

    private static ArrayList<Object> joinSequencedUnaryMinusOperators(ArrayList<Object> expression)
    {
        ArrayList<Object> result = new ArrayList<Object>(expression.size());

        int i = 0;
        while (i < expression.size())
        {
            if (isUnaryMinusOperatorAtIndex(expression, i) && isUnaryMinusOperatorAtIndex(expression, i + 1))
            {
                // Skip both minus operators
                i += 2;
                continue;
            }

            msArrayList.add(result, expression.get(i));
            i++;
        }

        return result;
    }

    private static boolean isUnaryMinusOperatorAtIndex(ArrayList<Object> expression, int index)
    {
        Object item = index < expression.size()
            ? expression.get(index)
            : null;

        return item instanceof UnaryMinusOperator;
    }

    private ArrayList<Object> parseExpression(String expression) throws Exception
    {
        mExpressionContext = new ExpressionContext(expression);
        ArrayList<Object> result = new ArrayList<Object>();
        boolean isFirstOperator = true;

        while (!mExpressionContext.isEof())
        {
            // Skip an optional equality sign at the beginning of the expression.
            if ((mExpressionContext.getPosition() == 0) && (mExpressionContext.getCurrentChar() == '='))
            {
                mExpressionContext.nextChar();

                // WORDSNET-5301 Conditional field is "< =" instead of "<=" in expression.
                if (mExpressionContext.isEof())
                    return null;
            }

            mExpressionContext.skipWhitespace();

            if (mExpressionContext.isEof() || mExpressionContext.getCurrentChar() == '\\')
                break;

            Operator _operator = parseOperator(isFirstOperator);
            if (_operator != null)
            {
                msArrayList.add(result, _operator);
                isFirstOperator = !_operator.isRightParenthesis();
            }
            else
            {
                IExecutionItem operand = parseOperand();
                msArrayList.add(result, operand);
                isFirstOperator = false;
            }
        }

        return result;
    }

    private ExecutionQueue buildExecutionQueue(ArrayList<Object> expression)
    {
        mExecutionQueue = new ExecutionQueue();
        mDeferredExecutionQueue = new ExecutionQueue();
        mOperatorStack = new OperatorStack();
        mDeferExecutionItems = false;

        if (expression.size() > 0)
        {
            Operator _operator = ms.as(expression.get(0), Operator.class);
            if (_operator != null && expression.get(0) instanceof IExecutionItem && !_operator.isUnary())
                return null;
        }

        for (Object item : expression)
        {
            Operator _operator = ms.as(item, Operator.class);
            if (_operator != null)
            {
                if (!processOperator(_operator))
                    return null;

                continue;
            }

            IExecutionItem executionItem = ms.as(item, IExecutionItem.class);
            if (executionItem != null)
            {
                processOperand(executionItem);
                continue;
            }

            Debug.assertTrue(false);
        }

        // If execution items are deferred at this point then being parsed expression has invalid syntax,
        // because of unclosed parenthesis.
        if (mDeferExecutionItems)
            return null;

        // There is no need to call ProcessDeferredExecutionQueue forcibly because the only way
        // deferred execution queue may be not empty at this point is that operator stack is not empty.
        // But this case is handled below.
        while (mOperatorStack.getCount() > 0)
        {
            Operator _operator = mOperatorStack.pop();

            // if @operator doesn't implement IExecutionItem, then being parsed expression has invalid syntax.
            // E.g. mOperatorStack can contain instances of LeftParenthesisOperator or RightParenthesisOperator
            // (both of these inheritors of Operator do not implement IExecutionItem interface) at this stage,
            // which means that the expression contains extra parentheses.
            // return null, that indicates syntax error.
            IExecutionItem executionItem = ms.as(_operator, IExecutionItem.class);
            if (executionItem != null)
            {
                enqueueExecutionItem(executionItem);
            }
            else
            {
                return null;
            }
        }

        return mExecutionQueue;
    }

    /// <summary>
    /// Parses an operator at the current expression position.
    /// </summary>
    /// <param name="isFirstOperator">True to indicate this is the first operator after an operand.</param>
    /// <returns>An <see cref="Operator"/> implementation or null if no operator has been recognized.</returns>
    private Operator parseOperator(boolean isFirstOperator)
    {
        if (mExpressionContext.compareAndAdvanceIfSuccessful("+"))
            return new AdditionOperator();

        if (mExpressionContext.compareAndAdvanceIfSuccessful("-"))
        {
            if (isFirstOperator)
                return new UnaryMinusOperator();
            else
                return new SubtractionOperator();
        }

        if (mExpressionContext.compareAndAdvanceIfSuccessful("%"))
            return new UnaryPercentOperator();

        if (mExpressionContext.compareAndAdvanceIfSuccessful("*"))
            return new MultiplicationOperator();

        if (mExpressionContext.compareAndAdvanceIfSuccessful("/"))
            return new DivisionOperator();

        if (mExpressionContext.compareAndAdvanceIfSuccessful("^"))
            return new ExponentiationOperator();

        if (mExpressionContext.compareAndAdvanceIfSuccessful("="))
            return new EqualityOperator();

        if (mExpressionContext.compareAndAdvanceIfSuccessful(">="))
            return new GreaterThanOrEqualToOperator();

        if (mExpressionContext.compareAndAdvanceIfSuccessful(">"))
            return new GreaterThanOperator();

        if (mExpressionContext.compareAndAdvanceIfSuccessful("<="))
            return new LessThanOrEqualToOperator();

        if (mExpressionContext.compareAndAdvanceIfSuccessful("<>"))
            return new InequalityOperator();

        if (mExpressionContext.compareAndAdvanceIfSuccessful("<"))
            return new LessThanOperator();

        if (mExpressionContext.compareAndAdvanceIfSuccessful("("))
            return new LeftParenthesisOperator();

        if (mExpressionContext.compareAndAdvanceIfSuccessful(")"))
            return new RightParenthesisOperator();

        return null;
    }

    private boolean processOperator(Operator _operator)
    {
        if (_operator.isLeftParenthesis())
        {
            // If execution items are deferred already then deferred execution items must be flushed because parentheses
            // may be interpreted as unary minus operator only when contain nothing but double constant optionally in
            // conjunction with unary non-minus operators, that is they can not contain nested parentheses in this case.
            if (mDeferExecutionItems)
                processDeferredExecutionQueue();
            else
                mDeferExecutionItems = true;
        }

        if ((mOperatorStack.getCount() == 0) || (_operator.isLeftParenthesis()))
        {
            mOperatorStack.push(_operator);
            return true;
        }

        if (_operator.isRightParenthesis())
        {
            while (mOperatorStack.getCount() > 0)
            {
                Operator stackOperator = mOperatorStack.pop();
                if (!stackOperator.isLeftParenthesis())
                {
                    IExecutionItem executionItem = ms.as(stackOperator, IExecutionItem.class);

                    if (executionItem != null)
                        enqueueExecutionItem(executionItem);
                    else
                        return false;
                }
                else
                {
                    if (mDeferExecutionItems)
                    {
                        // Parentheses are interpreted as unary minus operator, so add it.
                        mDeferExecutionItems = false;
                        enqueueExecutionItem(new UnaryMinusOperator(true));
                    }

                    return true;
                }
            }

            return true;
        }

        // Parentheses can not contain not unary or unary minus operator to be interpreted as unary minus operator.
        if (mDeferExecutionItems && (!_operator.isUnary() || _operator.isUnaryMinus()))
            mDeferExecutionItems = false;

        while (mOperatorStack.getCount() > 0)
        {
            if (_operator.getOrder() >= mOperatorStack.peek().getOrder())
                enqueueExecutionItem((IExecutionItem)mOperatorStack.pop());
            else
                break;
        }

        mOperatorStack.push(_operator);
        return true;
    }

    private IExecutionItem parseOperand() throws Exception
    {
        String operand = getSubstringBeforeDelimiter();

        Function functionItem = parseFunction(operand);
        if (functionItem != null)
        {
            // WORDSNET-13860 Whitespace between function name and opening parenthesis.
            mExpressionContext.skipWhitespace();

            if ((!mExpressionContext.isEof()) && (mExpressionContext.getCurrentChar() == '('))
                parseFunctionParameters(functionItem);

            return functionItem;
        }

        // A numeric constant?
        IExecutionItem executionItem = parseConstant(operand);
        if (executionItem != null)
            return executionItem;

        // Nothing worked above, so treat it as a string constant.
        return new StringConstant(operand);
    }

    private void processOperand(IExecutionItem operand)
    {
        // Parentheses can not contain not double constant operand to be interpreted as unary minus operator.
        if (mDeferExecutionItems && !(operand instanceof DoubleConstant))
            mDeferExecutionItems = false;

        enqueueExecutionItem(operand);
    }

    private void enqueueExecutionItem(IExecutionItem executionItem)
    {
        if (mDeferExecutionItems)
        {
            mDeferredExecutionQueue.enqueue(executionItem);
        }
        else
        {
            processDeferredExecutionQueue();
            mExecutionQueue.enqueue(executionItem);
        }
    }

    /// <summary>
    /// Flushes all deferred execution items to execution queue.
    /// </summary>
    private void processDeferredExecutionQueue()
    {
        while (mDeferredExecutionQueue.getCount() != 0)
        {
            mExecutionQueue.enqueue(mDeferredExecutionQueue.dequeue());
        }
    }

    /// <summary>
    /// Tries to parse the specified operand string to a function.
    /// </summary>
    /// <param name="operand">The operand string.</param>
    /// <returns>The parsed function or null if the operand is not a function.</returns>
    private static Function parseFunction(String operand)
    {
        switch (gStringSwitchMap.of(operand.toUpperCase()))
        {
            case /*"ABS"*/0:
                return new AbsFunction();
            case /*"AND"*/1:
                return new AndFunction();
            case /*"AVERAGE"*/2:
                return new AverageFunction();
            case /*"COUNT"*/3:
                return new CountFunction();
            case /*"DEFINED"*/4:
                return new DefinedFunction();
            case /*"FALSE"*/5:
                return new FalseFunction();
            case /*"IF"*/6:
                return new IfFunction();
            case /*"INT"*/7:
                return new IntFunction();
            case /*"MAX"*/8:
                return new MaxFunction();
            case /*"MIN"*/9:
                return new MinFunction();
            case /*"MOD"*/10:
                return new ModFunction();
            case /*"NOT"*/11:
                return new NotFunction();
            case /*"OR"*/12:
                return new OrFunction();
            case /*"PRODUCT"*/13:
                return new ProductFunction();
            case /*"ROUND"*/14:
                return new RoundFunction();
            case /*"SIGN"*/15:
                return new SignFunction();
            case /*"SUM"*/16:
                return new SumFunction();
            case /*"TRUE"*/17:
                return new TrueFunction();
            default:
                return null;
        }
    }

    private void parseFunctionParameters(Function function) throws Exception
    {
        boolean noString = true;
        int nestingLevel = 0;

        char listSeparator = FormatterPal.getListSeparatorCurrent();
        char numberGroupSeparator = FormatterPal.getNumberGroupSeparatorCurrent();

        char[] allSpecialChars = new char[4];
        allSpecialChars[0] = '"';
        allSpecialChars[1] = '(';
        allSpecialChars[2] = ')';
        allSpecialChars[3] = listSeparator;

        boolean checkNumbers = listSeparator == numberGroupSeparator;

        char[] quoteChar = { '"' };

        mExpressionContext.nextChar();
        int startIndex = mExpressionContext.getPosition();
        String numberParameter = "";
        char[] specialChars = allSpecialChars;
        while (!mExpressionContext.isEof())
        {
            // tries to find a next special char
            if (!mExpressionContext.findAny(specialChars))
            {
                break;
            }

            switch (mExpressionContext.getCurrentChar())
            {
                case '"':
                    {
                        // all special chars before the next quote are ignored, so we don't need to look for them
                        specialChars = noString ? quoteChar : allSpecialChars;
                        noString = !noString;
                        break;
                    }
                case '(':
                    {
                        nestingLevel++;
                        break;
                    }
                case ')':
                    {
                        if (nestingLevel == 0)
                        {
                            if (checkNumbers)
                            {
                                numberParameter = processParameter(function, startIndex, numberParameter);
                            }
                            else
                            {
                                evaluateAndAddFunctionParameter(function, startIndex);
                            }

                            mExpressionContext.nextChar();
                            if (StringUtil.hasChars(numberParameter))
                            {
                                evaluateAndAddFunctionParameter(function, numberParameter);
                            }

                            return;
                        }
                        else
                        {
                            nestingLevel--;
                        }

                        break;
                    }
                // in case of any list separator chars
                default:
                    {
                        if (nestingLevel == 0)
                        {
                            if (checkNumbers)
                            {
                                numberParameter = processParameter(function, startIndex, numberParameter);
                            }
                            else
                            {
                                evaluateAndAddFunctionParameter(function, startIndex);
                            }

                            startIndex = mExpressionContext.getPosition() + 1;
                        }
                        break;
                    }
            }

            mExpressionContext.nextChar();
        }

        if (StringUtil.hasChars(numberParameter))
        {
            evaluateAndAddFunctionParameter(function, numberParameter);
        }

        evaluateAndAddFunctionParameter(function, startIndex);
    }

    /// <summary>
    /// Checks whether a parsed parameter is a next part of number and process it.
    /// (if decimal group separator equals to list separator.)
    /// </summary>
    /// <param name="function">A function which parameter is belong to.</param>
    /// <param name="startIndex">A position in the being parsed expression where parameter start.</param>
    /// <param name="numberParameter">A number parameter that is parsed before.</param>
    /// <returns>A number parameter.</returns>
    private String processParameter(Function function, int startIndex, String numberParameter) throws Exception
    {
        String parameterExpression = mExpressionContext.getExpression().substring((startIndex), (startIndex) + (mExpressionContext.getPosition() - startIndex));
        FunctionParameter functionParameter = new FunctionParameter(parameterExpression);
        // check whether parameter is not a number
        if (!functionParameter.isNumber())
        {
            // WORDSNET-19391 Handle a situation when list separator == group separator and the expression continues.
            if (functionParameter.containsOperator() && functionParameter.getNumberOfDigitsAtStart() == 3)
                return concatenateNumberAndFunctionParameters(numberParameter, functionParameter);

            if (StringUtil.hasChars(numberParameter))
                evaluateAndAddFunctionParameter(function, numberParameter);

            evaluateAndAddFunctionParameter(function, functionParameter.getParameterValue());
            return "";
        }

        // check whether parameter isn't a next number part but a start of next number.
        if (functionParameter.isSpaceBefore() || functionParameter.isCurrencyAtStart() ||
            functionParameter.isNegative() || functionParameter.getLengthOfIntegerPart() < 3)
        {
            if (StringUtil.hasChars(numberParameter))
                evaluateAndAddFunctionParameter(function, numberParameter);

            numberParameter = functionParameter.getParameterValue();
        }
        else
        {
            numberParameter = concatenateNumberAndFunctionParameters(numberParameter, functionParameter);
        }

        // check whether parameter is a last part of number
        if (functionParameter.isCurrencyAtEnd() || functionParameter.isFractional() || functionParameter.isSpaceAfter())
        {
            evaluateAndAddFunctionParameter(function, numberParameter);
            return "";
        }

        return numberParameter;
    }

    private static String concatenateNumberAndFunctionParameters(String numberParameter, FunctionParameter functionParameter)
    {
        String separator = (StringUtil.containsOnlyWhitespaces(numberParameter) ||
                            StringUtil.containsOnlyWhitespaces(functionParameter.getParameterValue()))
            ? ""
            : Character.toString(FormatterPal.getNumberGroupSeparatorCurrent());

        return msString.concat(numberParameter, separator, functionParameter.getParameterValue());
    }

    private void evaluateAndAddFunctionParameter(Function function, int startIndex) throws Exception
    {
        String parameterExpression = msString.trim(mExpressionContext.getExpression().substring((startIndex), (startIndex) + (mExpressionContext.getPosition() - startIndex)));
        evaluateAndAddFunctionParameter(function, parameterExpression);
    }

    private void evaluateAndAddFunctionParameter(Function function, String parameterExpression) throws Exception
    {
        // Try to parse a cell reference.
        if (function.isAggregate())
        {
            IExecutionItem cellReference = CellReference.tryParse(mFieldContext, parameterExpression);
            if (cellReference != null)
            {
                function.getParameters().add(cellReference);
                return;
            }
        }

        Constant parameter = ExpressionEvaluator.evaluateExpression(mFieldContext, parameterExpression);
        function.getParameters().add(parameter);
    }

    /// <summary>
    /// Tries to parse the specified operand string to a constant.
    /// </summary>
    /// <param name="operand">The operand to parse.</param>
    /// <returns>The parsed constant or null if the operand is not a constant.</returns>
    private static IExecutionItem parseConstant(String operand)
    {
        return DoubleConstant.tryParse(operand);
    }

    /// <summary>
    /// Returns a substring of the expression string starting at the current position and ending at the index of
    /// any of the delimiters or at the end of the string if no delimiters were encountered. Advances
    /// the position as appropriate.
    /// </summary>
    /// <returns>The substring.</returns>
    private String getSubstringBeforeDelimiter()
    {
        StringBuilder builder = new StringBuilder();
        boolean whiteSpace = false;
        boolean normal = false;

        while (mExpressionContext.getPosition() < mExpressionContext.getExpression().length())
        {
            char c = mExpressionContext.getCurrentChar();
            /*CharType*/int charType = getCharType(c, builder);

            boolean breakLoop = false;
            switch (charType)
            {
                case CharType.NORMAL:
                    if (!whiteSpace)
                    {
                        normal = true;
                        builder.append(c);
                    }
                    else
                        breakLoop = true;
                    break;
                case CharType.WHITE_SPACE:
                    if (normal)
                        whiteSpace = true;
                    break;
                case CharType.DELIMITER:
                    breakLoop = true;
                    break;
                case CharType.CURRENCY:
                    builder.append(c);
                    break;
                default:
                    throw new IllegalArgumentException();
            }

            if (breakLoop)
                break;

            mExpressionContext.nextChar();
        }

        return msString.trim(builder.toString());
    }

    private /*CharType*/int getCharType(char c, StringBuilder builder)
    {
        if (msChar.isWhiteSpace(c))
        {
            char groupSeparator = FormatterPal.getNumberGroupSeparatorCurrent();
            if (c != groupSeparator)
                return CharType.WHITE_SPACE;
        }

        if (isDelimiter(c, builder))
            return CharType.DELIMITER;

        // TODO: multiple char currency support
        String currencySymbol = FormatterPal.getCurrencySymbolCurrent();
        if ((currencySymbol.length() == 1) && (c == currencySymbol.charAt(0)))
            return CharType.CURRENCY;

        return CharType.NORMAL;
    }

    /// <summary>
    /// Checks if the specified character is a delimiter.
    /// </summary>
    /// <param name="c">The character to check.</param>
    /// <param name="builder"></param>
    /// <returns>True if the character is a delimiter.</returns>
    private boolean isDelimiter(char c, StringBuilder builder)
    {
        switch (c)
        {
            case '+':
            case '-':
            case '*':
            case '/':
            case '^':
            case '>':
            case '<':
            case '=':
            case '%':
            case '(':
            case ')':
            case '\\':
                return true;
            default:
                if (mBuilder.isDelimiter(c, mExpressionContext.getExpression(), mExpressionContext.getPosition(), builder) ||
                    isCultureDependentDelimiter(c))
                {
                    mExpressionContext.nextChar();
                    return true;
                }

                return false;
        }
    }

    private static boolean isCultureDependentDelimiter(char c)
    {
        if ((c != '.') && (c != ','))
            return false;

        char decimalSeparator = FormatterPal.getDecimalSeparatorCurrent();
        char groupSeparator = FormatterPal.getNumberGroupSeparatorCurrent();

        return (decimalSeparator != c) && (groupSeparator != c);
    }

    private ExpressionContext mExpressionContext;
    private /*final*/ FieldContext mFieldContext;
    private /*final*/ IExpressionParserBehavior mBuilder;
    private ExecutionQueue mExecutionQueue;
    private ExecutionQueue mDeferredExecutionQueue;
    private OperatorStack mOperatorStack;
    private boolean mDeferExecutionItems;

    private /*enum*/ final class CharType
    {
        private CharType(){}
        
        public static final int NORMAL = 0;
        public static final int WHITE_SPACE = 1;
        public static final int DELIMITER = 2;
        public static final int CURRENCY = 3;

        public static final int length = 4;
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"ABS",
		"AND",
		"AVERAGE",
		"COUNT",
		"DEFINED",
		"FALSE",
		"IF",
		"INT",
		"MAX",
		"MIN",
		"MOD",
		"NOT",
		"OR",
		"PRODUCT",
		"ROUND",
		"SIGN",
		"SUM",
		"TRUE"
	);

}

