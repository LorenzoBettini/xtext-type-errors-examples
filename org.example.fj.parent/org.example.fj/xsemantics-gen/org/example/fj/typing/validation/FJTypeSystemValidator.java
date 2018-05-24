package org.example.fj.typing.validation;

import com.google.inject.Inject;
import org.eclipse.xsemantics.runtime.validation.XsemanticsValidatorErrorGenerator;
import org.eclipse.xtext.validation.AbstractDeclarativeValidator;
import org.example.fj.typing.FJTypeSystem;

@SuppressWarnings("all")
public class FJTypeSystemValidator extends AbstractDeclarativeValidator {
  @Inject
  protected XsemanticsValidatorErrorGenerator errorGenerator;
  
  @Inject
  protected FJTypeSystem xsemanticsSystem;
  
  protected FJTypeSystem getXsemanticsSystem() {
    return this.xsemanticsSystem;
  }
}
