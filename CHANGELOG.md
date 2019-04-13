# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added
- Tests for line coverage [e5f6236](https://github.com/DeepseaPlatform/coastal/commit/e5f623608a2f62a6e79f94029ced39e7db9e58d2)
- Javadoc for ```za.ac.sun.cs.coastal``` package [dba5ce3](https://github.com/DeepseaPlatform/coastal/commit/dba5ce3acfe230c4d6942cf4cc011a5231462bef)
- Caching of models inside COASTAL [1e3022c](https://github.com/DeepseaPlatform/coastal/commit/1e3022cda63225c873a4dc68a6f0ced9cb8f3516)
- In-house solver [2dad358](https://github.com/DeepseaPlatform/coastal/commit/2dad358bd8c8134c998038f67adf18aaa2e8b3fe)
- Support for all Java primitive types [2dad358](https://github.com/DeepseaPlatform/coastal/commit/2dad358bd8c8134c998038f67adf18aaa2e8b3fe)
- Average speed in GUI (number of divers/surfers/refinements per second) [e395280](https://github.com/DeepseaPlatform/coastal/commit/e395280f8e8b573f012f491e62691ec3c91807c4)
- [Issue #23](https://github.com/DeepseaPlatform/coastal/issues/23) Options to write instrumented code to file and to the log [69a7255](https://github.com/DeepseaPlatform/coastal/commit/69a72555dd664427a872a6e52234430cfe2663aa)
- [Issue #25](https://github.com/DeepseaPlatform/coastal/issues/25) Add support for user specified program entry points and (command-line) arguments [3edc259](https://github.com/DeepseaPlatform/coastal/commit/3edc259910277c4d33e0e5e5313b47c2b6aa10e4)
- User specified seeds for RandomTesting strategy [159ed27](https://github.com/DeepseaPlatform/coastal/commit/159ed27e44f796923929fba7bc4c8517624d82a8)
- [Issue #24](https://github.com/DeepseaPlatform/coastal/issues/24) Tests for the RandomTesting strategy [159ed27](https://github.com/DeepseaPlatform/coastal/commit/159ed27e44f796923929fba7bc4c8517624d82a8)
- Hybrid fuzzer [dd5fb83](https://github.com/DeepseaPlatform/coastal/commit/dd5fb83ee98981f9187f5a0d8c384f7b5942b95a) [71d7cda](https://github.com/DeepseaPlatform/coastal/commit/71d7cda9645646bae36415a27a3ccd4f3afcca40) [aadcfc1](https://github.com/DeepseaPlatform/coastal/commit/aadcfc1289055c2343273b999af3097e3b1dc177)
- Add command-line option (```-quiet```) to switch off detailed log [daa866d](https://github.com/DeepseaPlatform/coastal/commit/daa866df324d66cf6692c85099053ea6bd4d95be)
- Add "Stop" button for the GUI. [cbb488b](https://github.com/DeepseaPlatform/coastal/commit/cbb488b20327aa12e4514a78b9b0565868523ed7)

### Changed
- Improved text-based drawing of path trees [2dad358](https://github.com/DeepseaPlatform/coastal/commit/2dad358bd8c8134c998038f67adf18aaa2e8b3fe)
- Better handling of exceptions [2dad358](https://github.com/DeepseaPlatform/coastal/commit/2dad358bd8c8134c998038f67adf18aaa2e8b3fe)
- Improved support for random testing [60eccd2](https://github.com/DeepseaPlatform/coastal/commit/60eccd27562304b69a991f6ecb14b65dd6e8ce84) [691c43c](https://github.com/DeepseaPlatform/coastal/commit/691c43c0dd02149d4664c7ec716caa6b4c7d194b)

### Deprecated
- ...

### Removed
- Removed dependency on GREEN [2dad358](https://github.com/DeepseaPlatform/coastal/commit/2dad358bd8c8134c998038f67adf18aaa2e8b3fe)

### Fixed
- Fixed bug with stack sizes
- Fixed bug with character description in in-house solver output
- Removed tricky COASTAL version code [#37](https://github.com/DeepseaPlatform/coastal/issues/37)

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
