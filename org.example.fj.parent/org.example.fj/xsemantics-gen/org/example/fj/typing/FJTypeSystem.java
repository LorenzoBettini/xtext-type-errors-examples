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
import org.example.fj.fj.FJThis;
import org.example.fj.fj.FJType;
import org.example.fj.fj.FJTypedElement;
import org.example.fj.fj.FjPackage;

@SuppressWarnings("all")
public class FJTypeSystem extends XsemanticsRuntimeSystem {
  public final static String SUPERCLASSES = "org.example.fj.typing.Superclasses";
  
  public final static String FIELDS = "org.example.fj.typing.Fields";
  
  public final static String METHODS = "org.example.fj.typing.Methods";
  
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
  
  private PolymorphicDispatcher<Result<FJType>> inferTypeDispatcher;
  
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
    	"subtypesequenceImpl", 4, "|-", "<<");
    superclassesDispatcher = buildPolymorphicDispatcher(
    	"superclassesImpl", 2);
    fieldsDispatcher = buildPolymorphicDispatcher(
    	"fieldsImpl", 2);
    methodsDispatcher = buildPolymorphicDispatcher(
    	"methodsImpl", 2);
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
  
  public Result<FJType> inferType(final FJExpression expression) {
    return inferType(new RuleEnvironment(), null, expression);
  }
  
  public Result<FJType> inferType(final RuleEnvironment _environment_, final FJExpression expression) {
    return inferType(_environment_, null, expression);
  }
  
  public Result<FJType> inferType(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final FJExpression expression) {
    try {
    	return inferTypeInternal(_environment_, _trace_, expression);
    } catch (Exception _e_inferType) {
    	return resultForFailure(_e_inferType);
    }
  }
  
  public Result<Boolean> subtype(final FJType left, final FJType right) {
    return subtype(new RuleEnvironment(), null, left, right);
  }
  
  public Result<Boolean> subtype(final RuleEnvironment _environment_, final FJType left, final FJType right) {
    return subtype(_environment_, null, left, right);
  }
  
  public Result<Boolean> subtype(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final FJType left, final FJType right) {
    try {
    	return subtypeInternal(_environment_, _trace_, left, right);
    } catch (Exception _e_subtype) {
    	return resultForFailure(_e_subtype);
    }
  }
  
  public Boolean subtypeSucceeded(final FJType left, final FJType right) {
    return subtypeSucceeded(new RuleEnvironment(), null, left, right);
  }
  
  public Boolean subtypeSucceeded(final RuleEnvironment _environment_, final FJType left, final FJType right) {
    return subtypeSucceeded(_environment_, null, left, right);
  }
  
  public Boolean subtypeSucceeded(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final FJType left, final FJType right) {
    try {
    	subtypeInternal(_environment_, _trace_, left, right);
    	return true;
    } catch (Exception _e_subtype) {
    	return false;
    }
  }
  
  public Result<Boolean> subtypesequence(final List<FJExpression> expressions, final List<? extends FJTypedElement> elements) {
    return subtypesequence(new RuleEnvironment(), null, expressions, elements);
  }
  
  public Result<Boolean> subtypesequence(final RuleEnvironment _environment_, final List<FJExpression> expressions, final List<? extends FJTypedElement> elements) {
    return subtypesequence(_environment_, null, expressions, elements);
  }
  
  public Result<Boolean> subtypesequence(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final List<FJExpression> expressions, final List<? extends FJTypedElement> elements) {
    try {
    	return subtypesequenceInternal(_environment_, _trace_, expressions, elements);
    } catch (Exception _e_subtypesequence) {
    	return resultForFailure(_e_subtypesequence);
    }
  }
  
  public Boolean subtypesequenceSucceeded(final List<FJExpression> expressions, final List<? extends FJTypedElement> elements) {
    return subtypesequenceSucceeded(new RuleEnvironment(), null, expressions, elements);
  }
  
  public Boolean subtypesequenceSucceeded(final RuleEnvironment _environment_, final List<FJExpression> expressions, final List<? extends FJTypedElement> elements) {
    return subtypesequenceSucceeded(_environment_, null, expressions, elements);
  }
  
  public Boolean subtypesequenceSucceeded(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final List<FJExpression> expressions, final List<? extends FJTypedElement> elements) {
    try {
    	subtypesequenceInternal(_environment_, _trace_, expressions, elements);
    	return true;
    } catch (Exception _e_subtypesequence) {
    	return false;
    }
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
  
  protected Result<FJType> inferTypeInternal(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final FJExpression expression) {
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
  
  protected Result<Boolean> subtypeInternal(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final FJType left, final FJType right) {
    try {
    	checkParamsNotNull(left, right);
    	return subtypeDispatcher.invoke(_environment_, _trace_, left, right);
    } catch (Exception _e_subtype) {
    	sneakyThrowRuleFailedException(_e_subtype);
    	return null;
    }
  }
  
  protected void subtypeThrowException(final String _error, final String _issue, final Exception _ex, final FJType left, final FJType right, final ErrorInformation[] _errorInformations) throws RuleFailedException {
    String _stringRep = this.stringRep(left);
    String _plus = (_stringRep + " is not a subtype of ");
    String _stringRep_1 = this.stringRep(right);
    String _plus_1 = (_plus + _stringRep_1);
    String error = _plus_1;
    throwRuleFailedException(error,
    	_issue, _ex, new ErrorInformation(null, null));
  }
  
  protected Result<Boolean> subtypesequenceInternal(final RuleEnvironment _environment_, final RuleApplicationTrace _trace_, final List<FJExpression> expressions, final List<? extends FJTypedElement> elements) {
    try {
    	checkParamsNotNull(expressions, elements);
    	return subtypesequenceDispatcher.invoke(_environment_, _trace_, expressions, elements);
    } catch (Exception _e_subtypesequence) {
    	sneakyThrowRuleFailedException(_e_subtypesequence);
    	return null;
    }
  }
  
  protected void subtypesequenceThrowException(final String _error, final String _issue, final Exception _ex, final List<FJExpression> expressions, final List<? extends FJTypedElement> elements, final ErrorInformation[] _errorInformations) throws RuleFailedException {
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
  
  protected Result<FJType> inferTypeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FJThis _this) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<FJType> _result_ = applyRuleTThis(G, _subtrace_, _this);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("TThis") + stringRepForEnv(G) + " |- " + stringRep(_this) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTThis) {
    	inferTypeThrowException(ruleName("TThis") + stringRepForEnv(G) + " |- " + stringRep(_this) + " : " + "FJType",
    		TTHIS,
    		e_applyRuleTThis, _this, new ErrorInformation[] {new ErrorInformation(_this)});
    	return null;
    }
  }
  
  protected Result<FJType> applyRuleTThis(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FJThis _this) throws RuleFailedException {
    
    return new Result<FJType>(_applyRuleTThis_1(G, _this));
  }
  
  private FJType _applyRuleTThis_1(final RuleEnvironment G, final FJThis _this) throws RuleFailedException {
    FJType _env = this.<FJType>env(G, "this", FJType.class);
    return _env;
  }
  
  protected Result<FJType> inferTypeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FJNew newExp) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<FJType> _result_ = applyRuleTNew(G, _subtrace_, newExp);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("TNew") + stringRepForEnv(G) + " |- " + stringRep(newExp) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTNew) {
    	inferTypeThrowException(ruleName("TNew") + stringRepForEnv(G) + " |- " + stringRep(newExp) + " : " + "FJType",
    		TNEW,
    		e_applyRuleTNew, newExp, new ErrorInformation[] {new ErrorInformation(newExp)});
    	return null;
    }
  }
  
  protected Result<FJType> applyRuleTNew(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FJNew newExp) throws RuleFailedException {
    List<FJField> fields = this.fieldsInternal(_trace_, newExp.getType().getClassref());
    /* G |- newExp.args << fields */
    EList<FJExpression> _args = newExp.getArgs();
    subtypesequenceInternal(G, _trace_, _args, fields);
    return new Result<FJType>(_applyRuleTNew_1(G, newExp));
  }
  
  private FJType _applyRuleTNew_1(final RuleEnvironment G, final FJNew newExp) throws RuleFailedException {
    FJType _type = newExp.getType();
    return _type;
  }
  
  protected Result<FJType> inferTypeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FJParamRef paramref) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<FJType> _result_ = applyRuleTParamRef(G, _subtrace_, paramref);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("TParamRef") + stringRepForEnv(G) + " |- " + stringRep(paramref) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTParamRef) {
    	inferTypeThrowException(ruleName("TParamRef") + stringRepForEnv(G) + " |- " + stringRep(paramref) + " : " + "FJType",
    		TPARAMREF,
    		e_applyRuleTParamRef, paramref, new ErrorInformation[] {new ErrorInformation(paramref)});
    	return null;
    }
  }
  
  protected Result<FJType> applyRuleTParamRef(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FJParamRef paramref) throws RuleFailedException {
    
    return new Result<FJType>(_applyRuleTParamRef_1(G, paramref));
  }
  
  private FJType _applyRuleTParamRef_1(final RuleEnvironment G, final FJParamRef paramref) throws RuleFailedException {
    FJType _type = paramref.getParameter().getType();
    return _type;
  }
  
  protected Result<FJType> inferTypeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FJMemberSelection selection) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<FJType> _result_ = applyRuleTSelection(G, _subtrace_, selection);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("TSelection") + stringRepForEnv(G) + " |- " + stringRep(selection) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTSelection) {
    	inferTypeThrowException(ruleName("TSelection") + stringRepForEnv(G) + " |- " + stringRep(selection) + " : " + "FJType",
    		TSELECTION,
    		e_applyRuleTSelection, selection, new ErrorInformation[] {new ErrorInformation(selection)});
    	return null;
    }
  }
  
  protected Result<FJType> applyRuleTSelection(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FJMemberSelection selection) throws RuleFailedException {
    /* G |- selection.receiver : var FJType receiverType */
    FJExpression _receiver = selection.getReceiver();
    FJType receiverType = null;
    Result<FJType> result = inferTypeInternal(G, _trace_, _receiver);
    checkAssignableTo(result.getFirst(), FJType.class);
    receiverType = (FJType) result.getFirst();
    
    final FJMember message = selection.getMessage();
    boolean _matched = false;
    if (message instanceof FJMethod) {
      _matched=true;
      /* G |- selection.args << message.params */
      EList<FJExpression> _args = selection.getArgs();
      EList<FJParameter> _params = ((FJMethod)message).getParams();
      subtypesequenceInternal(G, _trace_, _args, _params);
    }
    return new Result<FJType>(_applyRuleTSelection_1(G, selection));
  }
  
  private FJType _applyRuleTSelection_1(final RuleEnvironment G, final FJMemberSelection selection) throws RuleFailedException {
    FJType _type = selection.getMessage().getType();
    return _type;
  }
  
  protected Result<FJType> inferTypeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FJCast cast) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<FJType> _result_ = applyRuleTCast(G, _subtrace_, cast);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("TCast") + stringRepForEnv(G) + " |- " + stringRep(cast) + " : " + stringRep(_result_.getFirst());
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleTCast) {
    	inferTypeThrowException(ruleName("TCast") + stringRepForEnv(G) + " |- " + stringRep(cast) + " : " + "FJType",
    		TCAST,
    		e_applyRuleTCast, cast, new ErrorInformation[] {new ErrorInformation(cast)});
    	return null;
    }
  }
  
  protected Result<FJType> applyRuleTCast(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FJCast cast) throws RuleFailedException {
    /* G |- cast.expression : var FJType expType */
    FJExpression _expression = cast.getExpression();
    FJType expType = null;
    Result<FJType> result = inferTypeInternal(G, _trace_, _expression);
    checkAssignableTo(result.getFirst(), FJType.class);
    expType = (FJType) result.getFirst();
    
    /* G |- cast.type <: expType or G |- expType <: cast.type */
    {
      RuleFailedException previousFailure = null;
      try {
        /* G |- cast.type <: expType */
        FJType _type = cast.getType();
        subtypeInternal(G, _trace_, _type, expType);
      } catch (Exception e) {
        previousFailure = extractRuleFailedException(e);
        /* G |- expType <: cast.type */
        FJType _type_1 = cast.getType();
        subtypeInternal(G, _trace_, expType, _type_1);
      }
    }
    return new Result<FJType>(_applyRuleTCast_1(G, cast));
  }
  
  private FJType _applyRuleTCast_1(final RuleEnvironment G, final FJCast cast) throws RuleFailedException {
    FJType _type = cast.getType();
    return _type;
  }
  
  protected Result<Boolean> subtypeImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FJType left, final FJType right) throws RuleFailedException {
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
  
  protected Result<Boolean> applyRuleClassSubtyping(final RuleEnvironment G, final RuleApplicationTrace _trace_, final FJType left, final FJType right) throws RuleFailedException {
    /* left.classref == right.classref or superclasses(left.classref).contains(right.classref) */
    {
      RuleFailedException previousFailure = null;
      try {
        FJClass _classref = left.getClassref();
        FJClass _classref_1 = right.getClassref();
        boolean _equals = Objects.equal(_classref, _classref_1);
        /* left.classref == right.classref */
        if (!_equals) {
          sneakyThrowRuleFailedException("left.classref == right.classref");
        }
      } catch (Exception e) {
        previousFailure = extractRuleFailedException(e);
        boolean _contains = this.superclassesInternal(_trace_, left.getClassref()).contains(right.getClassref());
        /* superclasses(left.classref).contains(right.classref) */
        if (!_contains) {
          sneakyThrowRuleFailedException("superclasses(left.classref).contains(right.classref)");
        }
      }
    }
    return new Result<Boolean>(true);
  }
  
  protected Result<Boolean> subtypesequenceImpl(final RuleEnvironment G, final RuleApplicationTrace _trace_, final List<FJExpression> expressions, final List<FJTypedElement> typedElements) throws RuleFailedException {
    try {
    	final RuleApplicationTrace _subtrace_ = newTrace(_trace_);
    	final Result<Boolean> _result_ = applyRuleSubtypeSequence(G, _subtrace_, expressions, typedElements);
    	addToTrace(_trace_, new Provider<Object>() {
    		public Object get() {
    			return ruleName("SubtypeSequence") + stringRepForEnv(G) + " |- " + stringRep(expressions) + " << " + stringRep(typedElements);
    		}
    	});
    	addAsSubtrace(_trace_, _subtrace_);
    	return _result_;
    } catch (Exception e_applyRuleSubtypeSequence) {
    	subtypesequenceThrowException(ruleName("SubtypeSequence") + stringRepForEnv(G) + " |- " + stringRep(expressions) + " << " + stringRep(typedElements),
    		SUBTYPESEQUENCE,
    		e_applyRuleSubtypeSequence, expressions, typedElements, new ErrorInformation[] {});
    	return null;
    }
  }
  
  protected Result<Boolean> applyRuleSubtypeSequence(final RuleEnvironment G, final RuleApplicationTrace _trace_, final List<FJExpression> expressions, final List<FJTypedElement> typedElements) throws RuleFailedException {
    /* expressions.size == typedElements.size or fail error "expected " + typedElements.size + " arguments, but got " + expressions.size */
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
        /* fail error "expected " + typedElements.size + " arguments, but got " + expressions.size */
        int _size_2 = typedElements.size();
        String _plus = ("expected " + Integer.valueOf(_size_2));
        String _plus_1 = (_plus + " arguments, but got ");
        int _size_3 = expressions.size();
        String _plus_2 = (_plus_1 + Integer.valueOf(_size_3));
        String error = _plus_2;
        throwForExplicitFail(error, new ErrorInformation(null, null));
      }
    }
    final Iterator<FJTypedElement> typedElementsIterator = typedElements.iterator();
    for (final FJExpression exp : expressions) {
      /* G |- exp : var FJType expType */
      FJType expType = null;
      Result<FJType> result = inferTypeInternal(G, _trace_, exp);
      checkAssignableTo(result.getFirst(), FJType.class);
      expType = (FJType) result.getFirst();
      
      /* G |- expType <: typedElementsIterator.next.type */
      FJType _type = typedElementsIterator.next().getType();
      subtypeInternal(G, _trace_, expType, _type);
    }
    return new Result<Boolean>(true);
  }
}
