package org.example.fj.typing.validation;

import com.google.inject.Inject;
import org.eclipse.xsemantics.runtime.validation.XsemanticsValidatorErrorGenerator;
import org.eclipse.xtext.validation.Check;
import org.example.fj.fj.FJMemberSelection;
import org.example.fj.fj.FJMethod;
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
  public void checkMain(final FJProgram program) {
    errorGenerator.generateErrors(this,
    	getXsemanticsSystem().checkMain(program),
    		program);
  }
}
