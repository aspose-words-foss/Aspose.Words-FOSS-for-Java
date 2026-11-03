// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/10/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Interface for a visitor of layout node content items.
/// </summary>
interface IDmlDiagramLayoutNodeContentItemVisitor
{
    public void visitLayoutNode(DmlDiagramLayoutNode layoutNode);
    public void visitAlgorithm(DmlAlgorithm algorithm);
    public void visitChoose(DmlChoose choose);
    public void visitConstraintList(DmlConstraintList constraintList);
    public void visitRuleList(DmlNumericRuleList ruleList);
    public void visitShape(DmlDiagramShape shape);
    public void visitForEach(DmlForEach forEach);
    public void visitLayoutVariablePropertySet(DmlLayoutVariablePropertySet layoutVariablePropertySet);
    public void visitPresentationOf(DmlPresentationOf presentationOf);
}

