Quality-Check
=============


The goal of quality-check is to provide a small Java library for
basic runtime code quality checks. It provides similar features to
org.springframework.util.Assert or com.google.common.base.Preconditions
without the need to include big libraries or frameworks such as
Spring or Guava. The package quality-check tries to replace these
libraries and provide all the basic code quality checks you need.

The checks provided here are typically used to validate method
parameters and detect errors during runtime. To detect errors before
runtime we use JSR-305 Annotations. With these annotations you are
able to detect possible bugs earlier. For more informations look
at FindBugs™ JSR-305 support.

We noticed that many projects just copy
[org.springframework.util.Assert](http://static.springsource.org/spring/docs/3.1.x/javadoc-api/org/springframework/util/Assert.html)
or
[com.google.common.base.Preconditions](http://docs.guava-libraries.googlecode.com/git/javadoc/com/google/common/base/Preconditions.html)
to get access to these quality checks. Therefore we provide these
features and small library that can be included in any application
without big overhead.


License
-------

Please visit the Quality-Check web site for more information:

  [http://qualitycheck.sourceforge.net/](http://qualitycheck.sourceforge.net/)

Copyright 2012 André Rouél, Dominik Seichter

Dominik Seichter and André Rouél licenses this product to you under
the Apache License, version 2.0 (the "License"); you may not use
this product except in compliance with the License. You may obtain
a copy of the License at:

   [http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
implied.  See the License for the specific language governing
permissions and limitations under the License.
