package org.example.fj.typing;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.inject.Provider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xsemantics.runtime.ErrorInformation;
import org.eclipse.xsemantics.runtime.Result;
import org.eclipse.xsemantics.runtime.RuleApplicationTrace;
import org.eclipse.xsemantics.runtime.RuleEnvironment;
import org.eclipse.xsemantics.runtime.RuleFailedException;
import org.eclipse.xsemantics.runtime.XsemanticsRuntimeSystem;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.util.PolymorphicDispatcher;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.example.fj.fj.FJAccessLevel;
import org.example.fj.fj.FJCast;
import org.example.fj.fj.FJClass;
import org.example.fj.fj.FJExpression;
import org.example.fj.fj.FJField;
import org.example.fj.fj.FJMember;
import org.example.fj.fj.FJMemberSelection;
import org.example.fj.fj.FJMethod;
import org.example.fj.fj.FJNew;
import org.example.fj.fj.FJParamRef;
import org.example.fj.fj.FJParameter;
import org.example.fj.fj.FJProgram;
import org.example.fj.fj.FJThis;
import org.example.fj.fj.FJTypedElement;
import org.example.fj.fj.FjPackage;

@SuppressWarnings("all")
public class FJTypeSystem extends XsemanticsRuntimeSystem {
  public final static String SUPERCLASSES = "org.example.fj.typing.Superclasses";
  
  public final static String FIELDS = "org.example.fj.typing.Fields";
  
  public final static String METHODS = "org.example.fj.typing.Methods";
  
  public final static String ISACCESSIBLE = "org.example.fj.typing.IsAccessible";
  
  public final static String TTHIS = "org.example.fj.typing.TThis";
  
  public final static String TNEW = "org.example.fj.typing.TNew";
  
  public final static String TPARAMREF = "org.example.fj.typing.TParamRef";
  
  public final static String TSELECTION = "org.example.fj.typing.TSelection";
  
  public final static String TCAST = "org.example.fj.typing.TCast";
  
  public final static String CLASSSUBTYPING = "org.example.fj.typing.ClassSubtyping";
  
  public final static String SUBTYPESEQUENCE = "org.example.fj.typing.SubtypeSequence";
  
  private PolymorphicDispatcher<List<FJClass>> superclassesDispatcher;
  
  private PolymorphicDispatcher<List<FJField>> fieldsDispatcher;
  
  private PolymorphicDispatcher<List<FJMethod>> methodsDispatcher;
  
  private PolymorphicDispatcher<Boolean> isAccessibleDispatcher;
  
  private PolymorphicDispatcher<Result<FJClass>> inferTypeDispatcher;
  
  private PolymorphicDispatcher<Result<Boolean>> subtypeDispatcher;
  
  private PolymorphicDispatcher<Result<Boolean>> subtypesequenceDispatcher;
  
  public FJTypeSystem() {
    init();
  }
  
  public void init() {
    inferTypeDispatcher = buildPolymorphicDispatcher1(
    	"inferTypeImpl", 3, "|-", ":");
    subtypeDispatcher = buildPolymorphicDispatcher1(
    	"subtypeImpl", 4, "|-", "<:");
    subtypesequenceDispatcher = buildPolymorphicDispatcher1(
    	"subtypesequenceImpl", 5, "|-", "~>", "<<");
    superclassesDispatcher = buildPolymorphicDispatcher(
    	"superclassesImpl", 2);
    fieldsDispatcher = buildPolymorphicDispatcher(
    	"fieldsImpl", 2);
    methodsDispatcher = buildPolymorphicDispatcher(
    	"methodsImpl", 2);
    isAccessibleDispatcher = buildPolymorphicDispatcher(
    	"isAccessibleImpl", 3);
  }
  
  public List<FJClass> superclasses(final FJClass cl) throws RuleFailedException {
    return superclasses(null, cl);
  }
  
  public List<FJClass> superclasses(final RuleApplicationTrace _trace_, final FJClass cl) throws RuleFailedException {
    try {
    	return superclassesInternal(_trace_, cl);
    } catch (Exception _e_superclasses) {
    	throw extractRuleFailedException(_e_superclasses);
    }
  }
  
  public List<FJField> fields(final FJClass cl) throws RuleFailedException {
    return fields(null, cl);
  }
  
  public List<FJField> fields(final RuleApplicationTrace _trace_, final FJClass cl) throws RuleFailedException {
    try {
    	return fieldsInternal(_trace_, cl);
    } catch (Exception _e_fields) {
    	throw extractRuleFailedException(_e_fields);
    }
  }
  
  public List<FJMethod> methods(final FJClass cl) throws RuleFailedException {
    return methods(null, cl);
  }
  
  public List<FJMethod> methods(final RuleApplicationTrace _trace_, final FJClass cl) throws RuleFailedException {
    try {
    	return methodsInternal(_trace_, cl);
    } catch (Exception _e_methods) {
    	throw extractRuleFailedException(_e_methods);
    }
  }
  
  public Boolean isAccessible(final FJMember member, final EObject context) throws RuleFailedException {
    return isAccessible(null, member, context);
  }
  
  public Boolean isAccessible(final RuleApplicationTrace _trace_, final FJMember member, final EObject context) throws RuleFailedException {
    try {
    	return isAccessibleInternal(_trace_, member, context);
    } catch (Exception _e_isAccessible) {
    	throw extractRuleFailedException(_e_isAccessible);
    }
  }
  
  public Result<FJClass> inferType(final FJExpression expression) {
    return inferType(new RuleEnvironment(), null, expression);
  }
  
  public Result<FJClass> inferType(final RuleEnvironment _environment_, final FJExpression expression) {
    return inferType(_environment_, null, expression);
  }
  
  public Result<FJClass> inferType(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final FJExpression expression) {
    try {
    	return inferTypeInternal(_environment_, _trace_, expression);
    } catch (Exception _e_inferType) {
    	return resultForFailure(_e_inferType);
    }
  }
  
  public Result<Boolean> subtype(final FJClass left, final FJClass right) {
    return subtype(new RuleEnvironment(), null, left, right);
  }
  
  public Result<Boolean> subtype(final RuleEnvironment _environment_, final FJClass left, final FJClass right) {
    return subtype(_environment_, null, left, right);
  }
  
  public Result<Boolean> subtype(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final FJClass left, final FJClass right) {
    try {
    	return subtypeInternal(_environment_, _trace_, left, right);
    } catch (Exception _e_subtype) {
    	return resultForFailure(_e_subtype);
    }
  }
  
  public Boolean subtypeSucceeded(final FJClass left, final FJClass right) {
    return subtypeSucceeded(new RuleEnvironment(), null, left, right);
  }
  
  public Boolean subtypeSucceeded(final RuleEnvironment _environment_, final FJClass left, final FJClass right) {
    return subtypeSucceeded(_environment_, null, left, right);
  }
  
  public Boolean subtypeSucceeded(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final FJClass left, final FJClass right) {
    try {
    	subtypeInternal(_environment_, _trace_, left, right);
    	return true;
    } catch (Exception _e_subtype) {
    	return false;
    }
  }
  
  public Result<Boolean> subtypesequence(final FJExpression owner, final List<FJExpression> expressions, final List<? extends FJTypedElement> elements) {
    return subtypesequence(new RuleEnvironment(), null, owner, expressions, elements);
  }
  
  public Result<Boolean> subtypesequence(final RuleEnvironment _environment_, final FJExpression owner, final List<FJExpression> expressions, final List<? extends FJTypedElement> elements) {
    return subtypesequence(_environment_, null, owner, expressions, elements);
  }
  
  public Result<Boolean> subtypesequence(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final FJExpression owner, final List<FJExpression> expressions, final List<? extends FJTypedElement> elements) {
    try {
    	return subtypesequenceInternal(_environment_, _trace_, owner, expressions, elements);
    } catch (Exception _e_subtypesequence) {
    	return resultForFailure(_e_subtypesequence);
    }
  }
  
  public Boolean subtypesequenceSucceeded(final FJExpression owner, final List<FJExpression> expressions, final List<? extends FJTypedElement> elements) {
    return subtypesequenceSucceeded(new RuleEnvironment(), null, owner, expressions, elements);
  }
  
  public Boolean subtypesequenceSucceeded(final RuleEnvironment _environment_, final FJExpression owner, final List<FJExpression> expressions, final List<? extends FJTypedElement> elements) {
    return subtypesequenceSucceeded(_environment_, null, owner, expressions, elements);
  }
  
  public Boolean subtypesequenceSucceeded(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final FJExpression owner, final List<FJExpression> expressions, final List<? extends FJTypedElement> elements) {
    try {
    	subtypesequenceInternal(_environment_, _trace_, owner, expressions, elements);
    	return true;
    } catch (Exception _e_subtypesequence) {
    	return false;
    }
  }
  
  public Result<Boolean> checkNew(final FJNew newExp) {
    return checkNew(null, newExp);
  }
  
  public Result<Boolean> checkNew(final RuleApplicationTrace _trace_, final FJNew newExp) {
    try {
    	return checkNewInternal(_trace_, newExp);
    } catch (Exception _e_CheckNew) {
    	return resultForFailure(_e_CheckNew);
    }
  }
  
  protected Result<Boolean> checkNewInternal(final RuleApplicationTrace _trace_, final FJNew newExp) throws RuleFailedException {
    List<FJField> fields = this.fieldsInternal(_trace_, newExp.getType());
    /* 'this' <- getContainerOfType(newExp, FJClass) |- newExp ~> newExp.args << fields */
    EList<FJExpression> _args = newExp.getArgs();
    FJClass _containerOfType = EcoreUtil2.<FJClass>getContainerOfType(newExp, FJClass.class);
    subtypesequenceInternal(environmentEntry("this", _containerOfType), _trace_, newExp, _args, fields);
    return new Result<Boolean>(true);
  }
  
  public Result<Boolean> checkSelection(final FJMemberSelection selection) {
    return checkSelection(null, selection);
  }
  
  public Result<Boolean> checkSelection(final RuleApplicationTrace _trace_, final FJMemberSelection selection) {
    try {
    	return checkSelectionInternal(_trace_, selection);
    } catch (Exception _e_CheckSelection) {
    	return resultForFailure(_e_CheckSelection);
    }
  }
  
  protected Result<Boolean> checkSelectionInternal(final RuleApplicationTrace _trace_, final FJMemberSelection selection) throws RuleFailedException {
    final FJMember member = selection.getMember();
    boolean _matched = false;
    if (member instanceof FJMethod) {
      _matched=true;
      /* 'this' <- member.eContainer |- selection ~> selection.args << member.params or fail error previousFailure.previous.message source selection feature FJ_MEMBER_SELECTION__MEMBER */
      {
        RuleFailedException previousFailure = null;
        try {
          /* 'this' <- member.eContainer |- selection ~> selection.args << member.params */
          EList<FJExpression> _args = selection.getArgs();
          EList<FJParameter> _params = ((FJMethod)member).getParams();
          EObject _eContainer = ((FJMethod)member).eContainer();
          subtypesequenceInternal(environmentEntry("this", _eContainer), _trace_, selection, _args, _params);
        } catch (Exception e) {
          previousFailure = extractRuleFailedException(e);
          /* fail error previousFailure.previous.message source selection feature FJ_MEMBER_SELECTION__MEMBER */
          String _message = previousFailure.getPrevious().getMessage();
          String error = _message;
          EObject source = selection;
          EStructuralFeature feature = FjPackage.Literals.FJ_MEMBER_SELECTION__MEMBER;
          throwForExplicitFail(error, new ErrorInformation(source, feature));
        }
      }
    }
    return new Result<Boolean>(true);
  }
  
  public Result<Boolean> checkCast(final FJCast cast) {
    return checkCast(null, cast);
  }
  
  public Result<Boolean> checkCast(final RuleApplicationTrace _trace_, final FJCast cast) {
    try {
    	return checkCastInternal(_trace_, cast);
    } catch (Exception _e_CheckCast) {
    	return resultForFailure(_e_CheckCast);
    }
  }
  
  protected Result<Boolean> checkCastInternal(final RuleApplicationTrace _trace_, final FJCast cast) throws RuleFailedException {
    /* 'this' <- getContainerOfType(cast, FJClass) |- cast.expression : var FJClass expType */
    FJExpression _expression = cast.getExpression();
    FJClass expType = null;
    FJClass _containerOfType = EcoreUtil2.<FJClass>getContainerOfType(cast, FJClass.class);
    Result<FJClass> result = inferTypeInternal(environmentEntry("this", _containerOfType), _trace_, _expression);
    checkAssignableTo(result.getFirst(), FJClass.class);
    expType = (FJClass) result.getFirst();
    
    /* empty |- cast.type <: expType or empty |- expType <: cast.type or fail error "Cannot cast from " + stringRep(expType) + " to " + stringRep(cast.type) source cast */
    {
      RuleFailedException previousFailure = null;
      try {
        /* empty |- cast.type <: expType */
        FJClass _type = cast.getType();
        subtypeInternal(emptyEnvironment(), _trace_, _type, expType);
      } catch (Exception e) {
        previousFailure = extractRuleFailedException(e);
        /* empty |- expType <: cast.type or fail error "Cannot cast from " + stringRep(expType) + " to " + stringRep(cast.type) source cast */
        {
          try {
            /* empty |- expType <: cast.type */
            FJClass _type_1 = cast.getType();
            subtypeInternal(emptyEnvironment(), _trace_, expType, _type_1);
          } catch (Exception e_1) {
            previousFailure = extractRuleFailedException(e_1);
            /* fail error "Cannot cast from " + stringRep(expType) + " to " + stringRep(cast.type) source cast */
            String _stringRep = this.stringRep(expType);
            String _plus = ("Cannot cast from " + _stringRep);
            String _plus_1 = (_plus + " to ");
            String _stringRep_1 = this.stringRep(cast.getType());
            String _plus_2 = (_plus_1 + _stringRep_1);
            String error = _plus_2;
            EObject source = cast;
            throwForExplicitFail(error, new ErrorInformation(source, null));
          }
        }
      }
    }
    return new Result<Boolean>(true);
  }
  
  public Result<Boolean> checkMethodBody(final FJMethod method) {
    return checkMethodBody(null, method);
  }
  
  public Result<Boolean> checkMethodBody(final RuleApplicationTrace _trace_, final FJMethod method) {
    try {
    	return checkMethodBodyInternal(_trace_, method);
    } catch (Exception _e_CheckMethodBody) {
    	return resultForFailure(_e_CheckMethodBody);
    }
  }
  
  protected Result<Boolean> checkMethodBodyInternal(final RuleApplicationTrace _trace_, final FJMethod method) throws RuleFailedException {
    /* 'this' <- method.eContainer |- method.expression : var FJClass bodyType */
    FJExpression _expression = method.getExpression();
    FJClass bodyType = null;
    EObject _eContainer = method.eContainer();
    Result<FJClass> result = inferTypeInternal(environmentEntry("this", _eContainer), _trace_, _expression);
    checkAssignableTo(result.getFirst(), FJClass.class);
    bodyType = (FJClass) result.getFirst();
    
    /* empty |- bodyType <: method.type or fail error "Type mismatch: cannot convert from " + stringRep(bodyType) + " to " + stringRep(method.type) source method.expression */
    {
      RuleFailedException previousFailure = null;
      try {
        /* empty |- bodyType <: method.type */
        FJClass _type = method.getType();
        subtypeInternal(emptyEnvironment(), _trace_, bodyType, _type);
      } catch (Exception e) {
        previousFailure = extractRuleFailedException(e);
        /* fail error "Type mismatch: cannot convert from " + stringRep(bodyType) + " to " + stringRep(method.type) source method.expression */
        String _stringRep = this.stringRep(bodyType);
        String _plus = ("Type mismatch: cannot convert from " + _stringRep);
        String _plus_1 = (_plus + " to ");
        String _stringRep_1 = this.stringRep(method.getType());
        String _plus_2 = (_plus_1 + _stringRep_1);
        String error = _plus_2;
        FJExpression _expression_1 = method.getExpression();
        EObject source = _expression_1;
        throwForExplicitFail(error, new ErrorInformation(source, null));
      }
    }
    return new Result<Boolean>(true);
  }
  
  public Result<Boolean> checkMemberSelection(final FJMemberSelection sel) {
    return checkMemberSelection(null, sel);
  }
  
  public Result<Boolean> checkMemberSelection(final RuleApplicationTrace _trace_, final FJMemberSelection sel) {
    try {
    	return checkMemberSelectionInternal(_trace_, sel);
    } catch (Exception _e_CheckMemberSelection) {
    	return resultForFailure(_e_CheckMemberSelection);
    }
  }
  
  protected Result<Boolean> checkMemberSelectionInternal(final RuleApplicationTrace _trace_, final FJMemberSelection sel) throws RuleFailedException {
    final FJMember member = sel.getMember();
    if (((member instanceof FJField) && sel.isMethodinvocation())) {
      /* fail error "Method invocation on a field" source sel feature FJ_MEMBER_SELECTION__MEMBER */
      String error = "Method invocation on a field";
      EObject source = sel;
      EStructuralFeature feature = FjPackage.Literals.FJ_MEMBER_SELECTION__MEMBER;
      throwForExplicitFail(error, new ErrorInformation(source, feature));
    } else {
      if (((member instanceof FJMethod) && (!sel.isMethodinvocation()))) {
        /* fail error "Field selection on a method" source sel feature FJ_MEMBER_SELECTION__MEMBER */
        String error_1 = "Field selection on a method";
        EObject source_1 = sel;
        EStructuralFeature feature_1 = FjPackage.Literals.FJ_MEMBER_SELECTION__MEMBER;
        throwForExplicitFail(error_1, new ErrorInformation(source_1, feature_1));
      }
    }
    return new Result<Boolean>(true);
  }
  
  public Result<Boolean> checkAccessibility(final FJMemberSelection sel) {
    return checkAccessibility(null, sel);
  }
  
  public Result<Boolean> checkAccessibility(final RuleApplicationTrace _trace_, final FJMemberSelection sel) {
    try {
    	return checkAccessibilityInternal(_trace_, sel);
    } catch (Exception _e_CheckAccessibility) {
    	return resultForFailure(_e_CheckAccessibility);
    }
  }
  
  protected Result<Boolean> checkAccessibilityInternal(final RuleApplicationTrace _trace_, final FJMemberSelection sel) throws RuleFailedException {
    final FJMember member = sel.getMember();
    /* isAccessible(member, sel.receiver) or fail error "The " + member.access + " member " + member.name + " is not accessible here" source sel feature FJ_MEMBER_SELECTION__MEMBER */
    {
      RuleFailedException previousFailure = null;
      try {
        Boolean _isAccessible = this.isAccessibleInternal(_trace_, member, sel.getReceiver());
        /* isAccessible(member, sel.receiver) */
        if (!_isAccessible) {
          sneakyThrowRuleFailedException("isAccessible(member, sel.receiver)");
        }
      } catch (Exception e) {
        previousFailure = extractRuleFailedException(e);
        /* fail error "The " + member.access + " member " + member.name + " is not accessible here" source sel feature FJ_MEMBER_SELECTION__MEMBER */
        FJAccessLevel _access = member.getAccess();
        String _plus = ("The " + _access);
        String _plus_1 = (_plus + " member ");
        String _name = member.getName();
        String _plus_2 = (_plus_1 + _name);
        String _plus_3 = (_plus_2 + " is not accessible here");
        String error = _plus_3;
        EObject source = sel;
        EStructuralFeature feature = FjPackage.Literals.FJ_MEMBER_SELECTION__MEMBER;
        throwForExplicitFail(error, new ErrorInformation(source, feature));
      }
    }
    return new Result<Boolean>(true);
  }
  
  public Result<Boolean> checkMain(final FJProgram program) {
    return checkMain(null, program);
  }
  
  public Result<Boolean> checkMain(final RuleApplicationTrace _trace_, final FJProgram program) {
    try {
    	return checkMainInternal(_trace_, program);
    } catch (Exception _e_CheckMain) {
    	return resultForFailure(_e_CheckMain);
    }
  }
  
  protected Result<Boolean> checkMainInternal(final RuleApplicationTrace _trace_, final FJProgram program) throws RuleFailedException {
    /* program.main === null or empty |- program.main : var FJClass mainType */
    {
      RuleFailedException previousFailure = null;
      try {
        FJExpression _main = program.getMain();
        boolean _tripleEquals = (_main == null);
        /* program.main === null */
        if (!_tripleEquals) {
          sneakyThrowRuleFailedException("program.main === null");
        }
      } catch (Exception e) {
        previousFailure = extractRuleFailedException(e);
        /* empty |- program.main : var FJClass mainType */
        FJExpression _main_1 = program.getMain();
        FJClass mainType = null;
        Result<FJClass> result = inferTypeInternal(emptyEnvironment(), _trace_, _main_1);
        checkAssignableTo(result.getFirst(), FJClass.class);
        mainType = (FJClass) result.getFirst();
        
      }
    }
    return new Result<Boolean>(true);
  }
  
  protected List<FJClass> superclassesInternal(final RuleApplicationTrace _trace_, final FJClass cl) {
    try {
    	checkParamsNotNull(cl);
    	return superclassesDispatcher.invoke(_trace_, cl);
    } catch (Exception _e_superclasses) {
    	sneakyThrowRuleFailedException(_e_superclasses);
    	return null;
    }
  }
  
  protected void superclassesThrowException(final String _error, final String _issue, final Exception _ex, final FJClass cl, final ErrorInformation[] _errorInformations) throws RuleFailedException {
    throwRuleFailedException(_error, _issue, _ex, _errorInformations);
  }
  
  protected List<FJField> fieldsInternal(final RuleApplicationTrace _trace_, final FJClass cl) {
    try {
    	checkParamsNotNull(cl);
    	return fieldsDispatcher.invoke(_trace_, cl);
    } catch (Exception _e_fields) {
    	sneakyThrowRuleFailedException(_e_fields);
    	return null;
    }
  }
  
  protected void fieldsThrowException(final String _error, final String _issue, final Exception _ex, final FJClass cl, final ErrorInformation[] _errorInformations) throws RuleFailedException {
    throwRuleFailedException(_error, _issue, _ex, _errorInformations);
  }
  
  protected List<FJMethod> methodsInternal(final RuleApplicationTrace _trace_, final FJClass cl) {
    try {
    	checkParamsNotNull(cl);
    	return methodsDispatcher.invoke(_trace_, cl);
    } catch (Exception _e_methods) {
    	sneakyThrowRuleFailedException(_e_methods);
    	return null;
    }
  }
  
  protected void methodsThrowException(final String _error, final String _issue, final Exception _ex, final FJClass cl, final ErrorInformation[] _errorInformations) throws RuleFailedException {
    throwRuleFailedException(_error, _issue, _ex, _errorInformations);
  }
  
  protected Boolean isAccessibleInternal(final RuleApplicationTrace _trace_, final FJMember member, final EObject context) {
    try {
    	checkParamsNotNull(member, context);
    	return isAccessibleDispatcher.invoke(_trace_, member, context);
    } catch (Exception _e_isAccessible) {
    	return false;
    }
  }
  
  protected void isAccessibleThrowException(final String _error, final String _issue, final Exception _ex, final FJMember member, final EObject context, final ErrorInformation[] _errorInformations) throws RuleFailedException {
    throwRuleFailedException(_error, _issue, _ex, _errorInformations);
  }
  
  protected Result<FJClass> inferTypeInternal(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final FJExpression expression) {
    try {
    	checkParamsNotNull(expression);
    	return inferTypeDispatcher.invoke(_environment_, _trace_, expression);
    } catch (Exception _e_inferType) {
    	sneakyThrowRuleFailedException(_e_inferType);
    	return null;
    }
  }
  
  protected void inferTypeThrowException(final String _error, final String _issue, final Exception _ex, final FJExpression expression, final ErrorInformation[] _errorInformations) throws RuleFailedException {
    String _stringRep = this.stringRep(expression);
    String _plus = ("cannot type " + _stringRep);
    String error = _plus;
    EObject source = expression;
    throwRuleFailedException(error,
    	_issue, _ex, new ErrorInformation(source, null));
  }
  
  protected Result<Boolean> subtypeInternal(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final FJClass left, final FJClass right) {
    try {
    	checkParamsNotNull(left, right);
    	return subtypeDispatcher.invoke(_environment_, _trace_, left, right);
    } catch (Exception _e_subtype) {
    	sneakyThrowRuleFailedException(_e_subtype);
    	return null;
    }
  }
  
  protected void subtypeThrowException(final String _error, final String _issue, final Exception _ex, final FJClass left, final FJClass right, final ErrorInformation[] _errorInformations) throws RuleFailedException {
    String _name = left.getName();
    String _plus = (_name + " is not a subtype of ");
    String _name_1 = right.getName();
    String _plus_1 = (_plus + _name_1);
    String error = _plus_1;
    throwRuleFailedException(error,
    	_issue, _ex, new ErrorInformation(null, null));
  }
  
  protected Result<Boolean> subtypesequenceInternal(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final FJExpression owner, final List<FJExpression> expressions, final List<? extends FJTypedElement> elements) {
    try {
    	checkParamsNotNull(owner, expressions, elements);
    	return subtypesequenceDispatcher.invoke(_environment_, _trace_, owner, expressions, elements);
    } catch (Exception _e_subtypesequence) {
    	sneakyThrowRuleFailedException(_e_subtypesequence);
    	return null;
    }
  }
  
  protected void subtypesequenceThrowException(final String _error, final String _issue, final Exception _ex, final FJExpression owner, final List<FJExpression> expressions, final List<? extends FJTypedElement> elements, final ErrorInformation[] _errorInformations) throws RuleFailedException {
    throwRuleFailedException(_error, _issue, _ex, _errorInformations);
  }
  
  protected List<FJClass> superclassesImpl(final RuleApplicationTrace _trace_, final FJClass cl) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final List<FJClass> _result_ = applyAuxFunSuperclasses(_subtrace_, cl);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return auxFunName("superclasses") + "(" + stringRep(cl)+ ")" + " = " + stringRep(_result_);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyAuxFunSuperclasses) {
    	superclassesThrowException(auxFunName("superclasses") + "(" + stringRep(cl)+ ")",
    		SUPERCLASSES,
    		e_applyAuxFunSuperclasses, cl, new ErrorInformation[] {new ErrorInformation(cl)});
    	return null;
    }
  }
  
  protected List<FJClass> applyAuxFunSuperclasses(final RuleApplicationTrace _trace_, final FJClass cl) throws RuleFailedException {
    return this.<FJClass>getAll(cl, 
      FjPackage.eINSTANCE.getFJClass_Superclass(), 
      FjPackage.eINSTANCE.getFJClass_Superclass(), 
      FJClass.class);
  }
  
  protected List<FJField> fieldsImpl(final RuleApplicationTrace _trace_, final FJClass clazz) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final List<FJField> _result_ = applyAuxFunFields(_subtrace_, clazz);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return auxFunName("fields") + "(" + stringRep(clazz)+ ")" + " = " + stringRep(_result_);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyAuxFunFields) {
    	fieldsThrowException(auxFunName("fields") + "(" + stringRep(clazz)+ ")",
    		FIELDS,
    		e_applyAuxFunFields, clazz, new ErrorInformation[] {new ErrorInformation(clazz)});
    	return null;
    }
  }
  
  protected List<FJField> applyAuxFunFields(final RuleApplicationTrace _trace_, final FJClass clazz) throws RuleFailedException {
    ArrayList<FJField> _xblockexpression = null;
    {
      Iterable<FJField> fields = new ArrayList<FJField>();
      List<FJClass> _superclasses = this.superclassesInternal(_trace_, clazz);
      for (final FJClass superclass : _superclasses) {
        List<FJField> _typeSelect = EcoreUtil2.<FJField>typeSelect(superclass.getMembers(), FJField.class);
        Iterable<FJField> _plus = Iterables.<FJField>concat(_typeSelect, fields);
        fields = _plus;
      }
      List<FJField> _typeSelect_1 = EcoreUtil2.<FJField>typeSelect(clazz.getMembers(), FJField.class);
      Iterable<FJField> _plus_1 = Iterables.<FJField>concat(fields, _typeSelect_1);
      fields = _plus_1;
      final Iterable<FJField> _converted_fields = (Iterable<FJField>)fields;
      _xblockexpression = (CollectionLiterals.<FJField>newArrayList(((FJField[])Conversions.unwrapArray(_converted_fields, FJField.class))));
    }
    return _xblockexpression;
  }
  
  protected List<FJMethod> methodsImpl(final RuleApplicationTrace _trace_, final FJClass clazz) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final List<FJMethod> _result_ = applyAuxFunMethods(_subtrace_, clazz);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return auxFunName("methods") + "(" + stringRep(clazz)+ ")" + " = " + stringRep(_result_);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyAuxFunMethods) {
    	methodsThrowException(auxFunName("methods") + "(" + stringRep(clazz)+ ")",
    		METHODS,
    		e_applyAuxFunMethods, clazz, new ErrorInformation[] {new ErrorInformation(clazz)});
    	return null;
    }
  }
  
  protected List<FJMethod> applyAuxFunMethods(final RuleApplicationTrace _trace_, final FJClass clazz) throws RuleFailedException {
    List<FJMethod> _xblockexpression = null;
    {
      final List<FJMethod> methods = EcoreUtil2.<FJMethod>typeSelect(clazz.getMembers(), FJMethod.class);
      final Consumer<FJClass> _function = (FJClass c) -> {
        methods.addAll(EcoreUtil2.<FJMethod>typeSelect(c.getMembers(), FJMethod.class));
      };
      this.superclassesInternal(_trace_, clazz).forEach(_function);
      _xblockexpression = (methods);
    }
    return _xblockexpression;
  }
  
  protected Boolean isAccessibleImpl(final RuleApplicationTrace _trace_, final FJMember member, final EObject context) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Boolean _result_ = applyAuxFunIsAccessible(_subtrace_, member, context);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return auxFunName("isAccessible") + "(" + stringRep(member) + ", " + stringRep(context)+ ")" + " = " + stringRep(_result_);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyAuxFunIsAccessible) {
    	isAccessibleThrowException(auxFunName("isAccessible") + "(" + stringRep(member) + ", " + stringRep(context)+ ")",
    		ISACCESSIBLE,
    		e_applyAuxFunIsAccessible, member, context, new ErrorInformation[] {new ErrorInformation(member), new ErrorInformation(context)});
    	return false;
    }
  }
  
  protected Boolean applyAuxFunIsAccessible(final RuleApplicationTrace _trace_, final FJMember member, final EObject context) throws RuleFailedException {
    final FJClass receiverClass = EcoreUtil2.<FJClass>getContainerOfType(context, FJClass.class);
    final FJClass memberClass = EcoreUtil2.<FJClass>getContainerOfType(member, FJClass.class);
    boolean _or = false;
    if ((Objects.equal(member.getAccess(), FJAccessLevel.PUBLIC) || 
      (receiverClass == memberClass))) {
      _or = true;
    } else {
      boolean _xblockexpression = false;
      {
        /* empty |- receiverClass <: memberClass */
        subtypeInternal(emptyEnvironment(), _trace_, receiverClass, memberClass);
        FJAccessLevel _access = member.getAccess();
        /* member.access != FJAccessLevel.PRIVATE */
        if (!(!Objects.equal(_access, FJAccessLevel.PRIVATE))) {
          sneakyThrowRuleFailedException("member.access != FJAccessLevel.PRIVATE");
        }
        _xblockexpression = ((!Objects.equal(_access, FJAccessLevel.PRIVATE)));
      }
      _or = _xblockexpression;
    }
    return Boolean.valueOf(_or);
  }
  
  protected Result<FJClass> inferTypeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FJThis _this) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<FJClass> _result_ = applyRuleTThis(G, _subtrace_, _this);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("TThis") + stringRepForEnv(G) + " |- " + stringRep(_this) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTThis) {
    	tThisThrowException(e_applyRuleTThis, _this);
    	return null;
    }
  }
  
  protected Result<FJClass> applyRuleTThis(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FJThis _this) throws RuleFailedException {
    
    return new Result<FJClass>(_applyRuleTThis_1(G, _this));
  }
  
  private FJClass _applyRuleTThis_1(final RuleEnvironment G, final FJThis _this) throws RuleFailedException {
    FJClass _env = this.<FJClass>env(G, "this", FJClass.class);
    return _env;
  }
  
  private void tThisThrowException(final Exception e_applyRuleTThis, final FJThis _this) throws RuleFailedException {
    String error = "\'this\' cannot be used in the current context";
    EObject source = _this;
    throwRuleFailedException(error,
    	TTHIS, e_applyRuleTThis, new ErrorInformation(source, null));
  }
  
  protected Result<FJClass> inferTypeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FJNew newExp) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<FJClass> _result_ = applyRuleTNew(G, _subtrace_, newExp);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("TNew") + stringRepForEnv(G) + " |- " + stringRep(newExp) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTNew) {
    	inferTypeThrowException(ruleName("TNew") + stringRepForEnv(G) + " |- " + stringRep(newExp) + " : " + "FJClass",
    		TNEW,
    		e_applyRuleTNew, newExp, new ErrorInformation[] {new ErrorInformation(newExp)});
    	return null;
    }
  }
  
  protected Result<FJClass> applyRuleTNew(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FJNew newExp) throws RuleFailedException {
    
    return new Result<FJClass>(_applyRuleTNew_1(G, newExp));
  }
  
  private FJClass _applyRuleTNew_1(final RuleEnvironment G, final FJNew newExp) throws RuleFailedException {
    FJClass _type = newExp.getType();
    return _type;
  }
  
  protected Result<FJClass> inferTypeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FJParamRef paramref) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<FJClass> _result_ = applyRuleTParamRef(G, _subtrace_, paramref);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("TParamRef") + stringRepForEnv(G) + " |- " + stringRep(paramref) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTParamRef) {
    	inferTypeThrowException(ruleName("TParamRef") + stringRepForEnv(G) + " |- " + stringRep(paramref) + " : " + "FJClass",
    		TPARAMREF,
    		e_applyRuleTParamRef, paramref, new ErrorInformation[] {new ErrorInformation(paramref)});
    	return null;
    }
  }
  
  protected Result<FJClass> applyRuleTParamRef(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FJParamRef paramref) throws RuleFailedException {
    
    return new Result<FJClass>(_applyRuleTParamRef_1(G, paramref));
  }
  
  private FJClass _applyRuleTParamRef_1(final RuleEnvironment G, final FJParamRef paramref) throws RuleFailedException {
    FJClass _type = paramref.getParameter().getType();
    return _type;
  }
  
  protected Result<FJClass> inferTypeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FJMemberSelection selection) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<FJClass> _result_ = applyRuleTSelection(G, _subtrace_, selection);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("TSelection") + stringRepForEnv(G) + " |- " + stringRep(selection) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTSelection) {
    	inferTypeThrowException(ruleName("TSelection") + stringRepForEnv(G) + " |- " + stringRep(selection) + " : " + "FJClass",
    		TSELECTION,
    		e_applyRuleTSelection, selection, new ErrorInformation[] {new ErrorInformation(selection)});
    	return null;
    }
  }
  
  protected Result<FJClass> applyRuleTSelection(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FJMemberSelection selection) throws RuleFailedException {
    
    return new Result<FJClass>(_applyRuleTSelection_1(G, selection));
  }
  
  private FJClass _applyRuleTSelection_1(final RuleEnvironment G, final FJMemberSelection selection) throws RuleFailedException {
    FJClass _type = selection.getMember().getType();
    return _type;
  }
  
  protected Result<FJClass> inferTypeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FJCast cast) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<FJClass> _result_ = applyRuleTCast(G, _subtrace_, cast);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("TCast") + stringRepForEnv(G) + " |- " + stringRep(cast) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTCast) {
    	inferTypeThrowException(ruleName("TCast") + stringRepForEnv(G) + " |- " + stringRep(cast) + " : " + "FJClass",
    		TCAST,
    		e_applyRuleTCast, cast, new ErrorInformation[] {new ErrorInformation(cast)});
    	return null;
    }
  }
  
  protected Result<FJClass> applyRuleTCast(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FJCast cast) throws RuleFailedException {
    
    return new Result<FJClass>(_applyRuleTCast_1(G, cast));
  }
  
  private FJClass _applyRuleTCast_1(final RuleEnvironment G, final FJCast cast) throws RuleFailedException {
    FJClass _type = cast.getType();
    return _type;
  }
  
  protected Result<Boolean> subtypeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FJClass left, final FJClass right) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<Boolean> _result_ = applyRuleClassSubtyping(G, _subtrace_, left, right);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("ClassSubtyping") + stringRepForEnv(G) + " |- " + stringRep(left) + " <: " + stringRep(right);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleClassSubtyping) {
    	subtypeThrowException(ruleName("ClassSubtyping") + stringRepForEnv(G) + " |- " + stringRep(left) + " <: " + stringRep(right),
    		CLASSSUBTYPING,
    		e_applyRuleClassSubtyping, left, right, new ErrorInformation[] {new ErrorInformation(left), new ErrorInformation(right)});
    	return null;
    }
  }
  
  protected Result<Boolean> applyRuleClassSubtyping(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FJClass left, final FJClass right) throws RuleFailedException {
    /* left == right or superclasses(left).contains(right) */
    {
      RuleFailedException previousFailure = null;
      try {
        boolean _equals = Objects.equal(left, right);
        /* left == right */
        if (!_equals) {
          sneakyThrowRuleFailedException("left == right");
        }
      } catch (Exception e) {
        previousFailure = extractRuleFailedException(e);
        boolean _contains = this.superclassesInternal(_trace_, left).contains(right);
        /* superclasses(left).contains(right) */
        if (!_contains) {
          sneakyThrowRuleFailedException("superclasses(left).contains(right)");
        }
      }
    }
    return new Result<Boolean>(true);
  }
  
  protected Result<Boolean> subtypesequenceImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FJExpression owner, final List<FJExpression> expressions, final List<FJTypedElement> typedElements) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<Boolean> _result_ = applyRuleSubtypeSequence(G, _subtrace_, owner, expressions, typedElements);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("SubtypeSequence") + stringRepForEnv(G) + " |- " + stringRep(owner) + " ~> " + stringRep(expressions) + " << " + stringRep(typedElements);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleSubtypeSequence) {
    	subtypesequenceThrowException(ruleName("SubtypeSequence") + stringRepForEnv(G) + " |- " + stringRep(owner) + " ~> " + stringRep(expressions) + " << " + stringRep(typedElements),
    		SUBTYPESEQUENCE,
    		e_applyRuleSubtypeSequence, owner, expressions, typedElements, new ErrorInformation[] {new ErrorInformation(owner)});
    	return null;
    }
  }
  
  protected Result<Boolean> applyRuleSubtypeSequence(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FJExpression owner, final List<FJExpression> expressions, final List<FJTypedElement> typedElements) throws RuleFailedException {
    /* expressions.size == typedElements.size or fail error "expected " + typedElements.size + " arguments, but got " + expressions.size source owner */
    {
      RuleFailedException previousFailure = null;
      try {
        int _size = expressions.size();
        int _size_1 = typedElements.size();
        boolean _equals = (_size == _size_1);
        /* expressions.size == typedElements.size */
        if (!_equals) {
          sneakyThrowRuleFailedException("expressions.size == typedElements.size");
        }
      } catch (Exception e) {
        previousFailure = extractRuleFailedException(e);
        /* fail error "expected " + typedElements.size + " arguments, but got " + expressions.size source owner */
        int _size_2 = typedElements.size();
        String _plus = ("expected " + Integer.valueOf(_size_2));
        String _plus_1 = (_plus + " arguments, but got ");
        int _size_3 = expressions.size();
        String _plus_2 = (_plus_1 + Integer.valueOf(_size_3));
        String error = _plus_2;
        EObject source = owner;
        throwForExplicitFail(error, new ErrorInformation(source, null));
      }
    }
    final Iterator<FJTypedElement> typedElementsIterator = typedElements.iterator();
    for (final FJExpression exp : expressions) {
      /* G |- exp : var FJClass expType */
      FJClass expType = null;
      Result<FJClass> result = inferTypeInternal(G, _trace_, exp);
      checkAssignableTo(result.getFirst(), FJClass.class);
      expType = (FJClass) result.getFirst();
      
      final FJClass expected = typedElementsIterator.next().getType();
      /* G |- expType <: expected or fail error stringRep(expType) + " is not a subtype of " + stringRep(expected) source exp */
      {
        RuleFailedException previousFailure = null;
        try {
          /* G |- expType <: expected */
          subtypeInternal(G, _trace_, expType, expected);
        } catch (Exception e_1) {
          previousFailure = extractRuleFailedException(e_1);
          /* fail error stringRep(expType) + " is not a subtype of " + stringRep(expected) source exp */
          String _stringRep = this.stringRep(expType);
          String _plus_3 = (_stringRep + " is not a subtype of ");
          String _stringRep_1 = this.stringRep(expected);
          String _plus_4 = (_plus_3 + _stringRep_1);
          String error_1 = _plus_4;
          EObject source_1 = exp;
          throwForExplicitFail(error_1, new ErrorInformation(source_1, null));
        }
      }
    }
    return new Result<Boolean>(true);
  }
}
