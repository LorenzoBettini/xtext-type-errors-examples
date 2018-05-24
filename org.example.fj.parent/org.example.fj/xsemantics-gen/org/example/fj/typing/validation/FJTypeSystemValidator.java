package org.example.fj.typing.validation;

import com.google.inject.Inject;
import org.eclipse.xsemantics.runtime.validation.XsemanticsValidatorErrorGenerator;
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
}
