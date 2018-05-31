package org.example.fj.typing.validation;

import com.google.inject.Inject;
import org.eclipse.xsemantics.runtime.validation.XsemanticsValidatorErrorGenerator;
import org.eclipse.xtext.validation.Check;
import org.example.fj.fj.FJCast;
import org.example.fj.fj.FJMemberSelection;
import org.example.fj.fj.FJMethod;
import org.example.fj.fj.FJNew;
import org.example.fj.fj.FJProgram;
import org.example.fj.typing.FJTypeSystem;
import org.example.fj.validation.AbstractFJValidator;

@SuppressWarnings("all")
public class FJTypeSystemValidator extends AbstractFJValidator {
  @Inject
  protected XsemanticsValidatorErrorGenerator errorGenerator;
  
  @Inject
  protected FJTypeSystem xsemanticsSystem;
  
  protected FJTypeSystem getXsemanticsSystem() {
    return this.xsemanticsSystem;
  }
  
  @Check
  public void checkNew(final FJNew newExp) {
    errorGenerator.generateErrors(this,
    	getXsemanticsSystem().checkNew(newExp),
    		newExp);
  }
  
  @Check
  public void checkSelection(final FJMemberSelection selection) {
    errorGenerator.generateErrors(this,
    	getXsemanticsSystem().checkSelection(selection),
    		selection);
  }
  
  @Check
  public void checkCast(final FJCast cast) {
    errorGenerator.generateErrors(this,
    	getXsemanticsSystem().checkCast(cast),
    		cast);
  }
  
  @Check
  public void checkMethodBody(final FJMethod method) {
    errorGenerator.generateErrors(this,
    	getXsemanticsSystem().checkMethodBody(method),
    		method);
  }
  
  @Check
  public void checkMemberSelection(final FJMemberSelection sel) {
    errorGenerator.generateErrors(this,
    	getXsemanticsSystem().checkMemberSelection(sel),
    		sel);
  }
  
  @Check
  public void checkAccessibility(final FJMemberSelection sel) {
    errorGenerator.generateErrors(this,
    	getXsemanticsSystem().checkAccessibility(sel),
    		sel);
  }
  
  @Check
  public void checkMain(final FJProgram program) {
    errorGenerator.generateErrors(this,
    	getXsemanticsSystem().checkMain(program),
    		program);
  }
}
