quality-check
=============

The goal of quality-check is to provide a small Java library for basic runtime code quality checks. It provides similar features to org.springframework.util.Assert or com.google.common.base.Preconditions without the need to include big libraries or frameworks such as Spring or Guava. The package quality-check tries to replace these libraries and provide all the basic code quality checks you need.  The checks provided here are typically used to validate method parameters and detect errors during runtime. To detect errors before runtime we use JSR-305 Annotations. With these annotations you are able to detect possible bugs earlier. For more informations look at FindBugs™ JSR-305 support.  We noticed that many projects just copy org.springframework.util.Assert or com.google.common.base.Preconditions to get access to these quality checks. Therefore we provide these features and small library that can be included in any application without big overhead.

