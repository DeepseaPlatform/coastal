# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## [0.2.0] - Unreleased

### Added
- Documentation for some classes
  [22ccafc](https://github.com/DeepseaPlatform/coastal/commit/22ccafc762f2a679b6cec5410383f779f536d815)
  [878f159](https://github.com/DeepseaPlatform/coastal/commit/878f159064ac52508a6e199b2aec53f7bd1c1e24)
- Method names are now displayed next to frame in detailed log
  [f24a40d](https://github.com/DeepseaPlatform/coastal/commit/f24a40def8a1fe9c267777955ae6bcf8e59dea12)

### Changed
- Eclipse configurations have been tweaked to remove unnecessary information
  [b24da00](https://github.com/DeepseaPlatform/coastal/commit/b24da004b892a31a8b931e130e125d402e385e77)

### Deprecated
- ...

### Removed
- Unneeded libraries
- Examples have moved to a [separate project](https://github.com/DeepseaPlatform/coastal-examples)

### Fixed
- Fix bug in Z3 translation that caused mismatched bit vector sizes
  [f3c8218](https://github.com/DeepseaPlatform/coastal/commit/f3c8218031f32fb5f730738365f7ddd6b7f74349)
- [Issue #42](https://github.com/DeepseaPlatform/coastal/issues/42)
  Fix bug that execute symbolic instruction before related classes were initialized
  [227236e](https://github.com/DeepseaPlatform/coastal/commit/227236e3347dbaadfd3f97c71a157d322e9490b6)

## [0.1.0] - 2019.11.24

This list is incomplete because we did not commit often enough and did not exercise enough self-discipline to update this file carefully.

### Added
- Add gradle configuration to publish COASTAL on Maven Central Repository
- Add model for String.valueOf()
  [6711b3e](https://github.com/DeepseaPlatform/coastal/commit/6711b3eaae451ba743b37a2a2196286ce1305c83)
  [e36b38c](https://github.com/DeepseaPlatform/coastal/commit/e36b38cacdff5dd228d46656ec471fcb3ec73633)
- Add model for String.contains()
  [86db343](https://github.com/DeepseaPlatform/coastal/commit/86db343141bdc4558600dafc62ca9cf2732f6963)
- Add model for String.equals()
  [125140a](https://github.com/DeepseaPlatform/coastal/commit/125140a578625fb561d78fc73abd2089e9559758)
- Add model for String.<init>()
  [fc97dc5](https://github.com/DeepseaPlatform/coastal/commit/fc97dc577cf6861d12f3dff6d5e8abd2d85e6b5c)
- Add model for Character.isLetter()
  [57a451b](https://github.com/DeepseaPlatform/coastal/commit/57a451b79de786e7a4586459c4121f67bb683e81)
- Add model for StringBuilder
  [8bf651d](https://github.com/DeepseaPlatform/coastal/commit/8bf651d370f3fb62b60e26a0651e9f971438e9b8)
- [Issue #29](https://github.com/DeepseaPlatform/coastal/issues/29)
  Add instruction ``ANEWARRAY``
  [9116d42](https://github.com/DeepseaPlatform/coastal/commit/9116d42f1949edc04e8825f4427f6bd8519c35a0)
- Add instruction ``LOOKUPSWITCH``
  [68512dc](https://github.com/DeepseaPlatform/coastal/commit/68512dcc24b403ab4a096fa5939b1b78dd82e04e)
- Add instruction ``D2I``
  [d4ebe95](https://github.com/DeepseaPlatform/coastal/commit/d4ebe95be6e1526bfb868a002b9a71c7c4d003db)
  [2b58d95](https://github.com/DeepseaPlatform/coastal/commit/2b58d95edc79931462b0d6c8e5128eddea2eacba)
- Add instruction ``F2I``, ``F2L``, ``D2L``
  [cd46640](https://github.com/DeepseaPlatform/coastal/commit/cd466408230531d2bc3c268bc7954d20cebfcbc9)
- Add instruction ``I2D``, ``I2F``, ``FNEG``
  [21212ce](https://github.com/DeepseaPlatform/coastal/commit/21212cea251d5f7f2f0887aa26c5652b60fd1a2a)
- Add instruction ``L2D``
  [14603ff](https://github.com/DeepseaPlatform/coastal/commit/14603ffee306e365552ef13ad8126b3e39a2b260)
- Add instruction ``L2F``, ``L2I``
  [8672856](https://github.com/DeepseaPlatform/coastal/commit/86728562c8ffbaac9af5c6f1d1b24696edc1bf03)
- Add instruction ``INSTANCEOF``
  [ce24b0e](https://github.com/DeepseaPlatform/coastal/commit/ce24b0e7d9554b6e76a61dd6bbb319525253740b)
- Add instruction ``D2F``
  [43ad3b7](https://github.com/DeepseaPlatform/coastal/commit/43ad3b7e8cfdaae8b021f86a9596f5468700a9a2)
- Add instruction ``DASTORE``, ``FASTORE``, ``LASTORE``, ``BASTORE``, ``DALOAD``, ``FALOAD``, ``LALOAD``, ``BALOAD``
  [081cfb5](https://github.com/DeepseaPlatform/coastal/commit/081cfb51cef3342893784d92c7cf422298233d2d)
- Add SV-COMP 2019 as test cases.  (But for now these tests are ignored.)
  [e3939d1](https://github.com/DeepseaPlatform/coastal/commit/e3939d119e1f97f4d9d1458aa37734df4455b994)
- Tests for line coverage
  [e5f6236](https://github.com/DeepseaPlatform/coastal/commit/e5f623608a2f62a6e79f94029ced39e7db9e58d2)
- Javadoc for ``za.ac.sun.cs.coastal`` package
  [dba5ce3](https://github.com/DeepseaPlatform/coastal/commit/dba5ce3acfe230c4d6942cf4cc011a5231462bef)
- Caching of models inside COASTAL
  [1e3022c](https://github.com/DeepseaPlatform/coastal/commit/1e3022cda63225c873a4dc68a6f0ced9cb8f3516)
- In-house solver
  [2dad358](https://github.com/DeepseaPlatform/coastal/commit/2dad358bd8c8134c998038f67adf18aaa2e8b3fe)
- Support for all Java primitive types
  [2dad358](https://github.com/DeepseaPlatform/coastal/commit/2dad358bd8c8134c998038f67adf18aaa2e8b3fe)
- Average speed in GUI (number of divers/surfers/refinements per second)
  [e395280](https://github.com/DeepseaPlatform/coastal/commit/e395280f8e8b573f012f491e62691ec3c91807c4)
- [Issue #23](https://github.com/DeepseaPlatform/coastal/issues/23)
  Options to write instrumented code to file and to the log
  [69a7255](https://github.com/DeepseaPlatform/coastal/commit/69a72555dd664427a872a6e52234430cfe2663aa)
- [Issue #25](https://github.com/DeepseaPlatform/coastal/issues/25)
  Add support for user specified program entry points and (command-line) arguments
  [3edc259](https://github.com/DeepseaPlatform/coastal/commit/3edc259910277c4d33e0e5e5313b47c2b6aa10e4)
- User specified seeds for RandomTesting strategy
  [159ed27](https://github.com/DeepseaPlatform/coastal/commit/159ed27e44f796923929fba7bc4c8517624d82a8)
- [Issue #24](https://github.com/DeepseaPlatform/coastal/issues/24)
  Tests for the RandomTesting strategy
  [159ed27](https://github.com/DeepseaPlatform/coastal/commit/159ed27e44f796923929fba7bc4c8517624d82a8)
- Hybrid fuzzer
  [dd5fb83](https://github.com/DeepseaPlatform/coastal/commit/dd5fb83ee98981f9187f5a0d8c384f7b5942b95a)
  [71d7cda](https://github.com/DeepseaPlatform/coastal/commit/71d7cda9645646bae36415a27a3ccd4f3afcca40)
  [aadcfc1](https://github.com/DeepseaPlatform/coastal/commit/aadcfc1289055c2343273b999af3097e3b1dc177)
- Add command-line option (``-quiet``) to switch off detailed log
  [daa866d](https://github.com/DeepseaPlatform/coastal/commit/daa866df324d66cf6692c85099053ea6bd4d95be)
- Add "Stop" button for the GUI.
  [cbb488b](https://github.com/DeepseaPlatform/coastal/commit/cbb488b20327aa12e4514a78b9b0565868523ed7)
- Add display of inputs for each dive/surf
  [1bd9605](https://github.com/DeepseaPlatform/coastal/commit/1bd960517045fe9852dc1d1f27f35ee19b4dfd88)
- Add creation of symbolic variables
  [244e468](https://github.com/DeepseaPlatform/coastal/commit/244e4682df19bfd7a4c0b8d0bf93823b31934bbb)

### Changed
- Eclipse launch configurations for quiet and verbose runs
  [a9a5a30](https://github.com/DeepseaPlatform/coastal/commit/a9a5a3073bd2d1c5464678f9fdc86ba4b2eab2e4)
- [Issue #32](https://github.com/DeepseaPlatform/coastal/issues/32)
  Z3 path now set in coastal configuration.
  [7349894](https://github.com/DeepseaPlatform/coastal/commit/7349894a7be0397d30ad973e75ba96222abbbb29)
- Improved text-based drawing of path trees
  [2dad358](https://github.com/DeepseaPlatform/coastal/commit/2dad358bd8c8134c998038f67adf18aaa2e8b3fe)
- Better handling of exceptions
  [2dad358](https://github.com/DeepseaPlatform/coastal/commit/2dad358bd8c8134c998038f67adf18aaa2e8b3fe)
  [a8386c4](https://github.com/DeepseaPlatform/coastal/commit/a8386c4c7f6c327d263cd38b897b253fa24da041)
- Improved support for random testing
  [60eccd2](https://github.com/DeepseaPlatform/coastal/commit/60eccd27562304b69a991f6ecb14b65dd6e8ce84)
  [691c43c](https://github.com/DeepseaPlatform/coastal/commit/691c43c0dd02149d4664c7ec716caa6b4c7d194b)
- Improved fuzzing
- Updated documentation
  [5a8e5cd](https://github.com/DeepseaPlatform/coastal/commit/5a8e5cd7af9f2f00ce99a294dcb4067d54a9a3cc)
  [1d65545](https://github.com/DeepseaPlatform/coastal/commit/1d6554591e2c36c81ae1c7b620b299d3ddd3d1ca)
  [04eeef0](https://github.com/DeepseaPlatform/coastal/commit/04eeef0acbfb2797f8aaec3344c750eb2c266535)
  [173925e](https://github.com/DeepseaPlatform/coastal/commit/173925ebb0c79a05ced6affaa1edc5e60960f948)
  [3887c1f](https://github.com/DeepseaPlatform/coastal/commit/3887c1f943fa4e0cf089490084a8c2c8f76fe622)
  [5bdc976](https://github.com/DeepseaPlatform/coastal/commit/5bdc97693b33aa8162e00869320349bc744d921a)
- Change COASTAL configuration files from XML to Java properties
  [b2cec72](https://github.com/DeepseaPlatform/coastal/commit/b2cec7218f2cb5cbfeca21017f7eef129e5771ff)
  [8ce795b](https://github.com/DeepseaPlatform/coastal/commit/8ce795ba1896f7a4792b5144812d517f6b208c42)
  [7f51209](https://github.com/DeepseaPlatform/coastal/commit/7f51209f722408d2139130a22ad6f8470f153451)

### Removed
- Removed dependency on GREEN
  [2dad358](https://github.com/DeepseaPlatform/coastal/commit/2dad358bd8c8134c998038f67adf18aaa2e8b3fe)

### Fixed
- Fixed bug with stack sizes
- Fixed bug with character description in in-house solver output
- [Issue #37](https://github.com/DeepseaPlatform/coastal/issues/37)
  Removed tricky COASTAL version code
  [d18fc4f](https://github.com/DeepseaPlatform/coastal/commit/d18fc4fd7aafe9cc8386c4be6ade6a1a39058c75)
- Fix bug in generational strategy
  [0b3be3a](https://github.com/DeepseaPlatform/coastal/commit/0b3be3a6bcc8ec078e04b37867f4e8aea07a934a)
  [aab8c61](https://github.com/DeepseaPlatform/coastal/commit/aab8c61ca4f770961b3add7ad7eaaad199b2f5a1)
  [6437a3b](https://github.com/DeepseaPlatform/coastal/commit/6437a3ba3bacfe99767dd2695891e6afb68a81a8)
- Fix error in advanced DUP instructions
  [183dfdb](https://github.com/DeepseaPlatform/coastal/commit/183dfdb96109153b7974903eecdfe1cdf5b1abe3)

## [0.0.2] - 2018.12.14

### Added
- Divers and strategies run in separate threads
- Allow users to specify number of initial threads
- Introduce lightweight instrumentation for fuzzing
- Random testing [a436562](https://github.com/DeepseaPlatform/coastal/commit/a4365621dd3968b80819a0f316d5de88ced9724f)
- Reporting of queue waiting times
- Min/max bounds for all data types -- not just integers
- Generational path strategy [59fea07](https://github.com/DeepseaPlatform/coastal/commit/59fea07ad3d88462bf17bc60860f0d0d2a5f02b8)
- Line coverage observer [a436562](https://github.com/DeepseaPlatform/coastal/commit/a4365621dd3968b80819a0f316d5de88ced9724f)
- Primitive GUI to show progress
- Log file includes thread ids
- New test structure
- Read class files from JAR files

### Changed
- COASTAL now reads configuration from XML files
- Listener mechanism replaced with publish-subscribe broker
- Listeners changed to Observers
- Version reporting made more robust
- Path tree allows concurrent updates
- Improved class loading
- Reduced console output [e1cc755](https://github.com/DeepseaPlatform/coastal/commit/e1cc7557e6c8e4317a3a307046dae0ed615f241d)
- Significant refactoring to allow for lightweight testing and fuzzing
- [Issue #17](https://github.com/DeepseaPlatform/coastal/issues/17) Duplicate and constant conjuncts now appear in the path tree [d44d531](https://github.com/DeepseaPlatform/coastal/commit/d44d531309d9ffdb66a8af752e01f35f53e2df91)

### Fixed
- Travis CI docker setup [dd9bca9](https://github.com/DeepseaPlatform/coastal/commit/dd9bca9ced9369f31dd21c7d82d8701cb4468791)
- [Bug #15](https://github.com/DeepseaPlatform/coastal/issues/15): new symbolic character bounds were incorrect (String.java::charAt__I__C) [8d9b5ba](https://github.com/DeepseaPlatform/coastal/commit/8d9b5ba7da9d0d5b79210fbce9df8957e349ce0b)
- [Bug #19](https://github.com/DeepseaPlatform/coastal/issues/19): COASTAL incorrectly complained about too few runs when only surfers executed [032346f](https://github.com/DeepseaPlatform/coastal/commit/032346f4d9a12a54a59a660c26b95e959872ca07)
- [Bug #20](https://github.com/DeepseaPlatform/coastal/issues/20): Options in XML are now pased to the strategy/observer/delegate they are intended for.  They are also correctly accessed. [2bfa3e1](https://github.com/DeepseaPlatform/coastal/commit/2bfa3e1c1ef8ca761be6746a01f64fee3916ca84)
- [Bug #18](https://github.com/DeepseaPlatform/coastal/issues/18): New docker image works, despite remaining bug [cca94f7](https://github.com/DeepseaPlatform/coastal/commit/cca94f785a58969e7fd775f58118dc8c31506a8f)

## [0.0.1] - 2018.11.27

### Added
- Exception handling
- Symbolic operations in examples such as analysis termination and markers
- Some initial documentation
- Some initial testing
- Path/conjunct/time/execution limits
- Instruction coverage computation
- Support for java `switch` statements
- Improved configuration settings
- Additional examples
- Breadth-first and random strategies (in addition to depth-first)
- All features from DEEPSEA

### Changed
- Setting `coastal.recordmarks` replaced with marker listener
- Internally, (almost) all classes are made non-static

### Removed
- All `coastal.dump...` settings
