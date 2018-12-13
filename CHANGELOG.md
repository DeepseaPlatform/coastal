# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## [Unreleased]

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

### Deprecated
- ...

### Removed
- ...

### Fixed
- Travis CI docker setup [dd9bca9](https://github.com/DeepseaPlatform/coastal/commit/dd9bca9ced9369f31dd21c7d82d8701cb4468791)
- Bug #15: new symbolic character bounds were incorrect (String.java::charAt__I__C) [8d9b5ba](https://github.com/DeepseaPlatform/coastal/commit/8d9b5ba7da9d0d5b79210fbce9df8957e349ce0b)

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
