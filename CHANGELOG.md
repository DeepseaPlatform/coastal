# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added
- Read class files from JAR files
- New test structure
- Divers and strategies run in separate threads
- Log file includes thread ids
- Generational path strategy
- Min/max bounds for all data types -- not just integers
- Reporting of queue waiting times
- Primitive GUI to show progress
- Allow users to specify number of initial threads

### Changed
- COASTAL now reads configuration from XML files
- Listener mechanism replaced with publish-subscribe broker
- Listeners changed to Observers
- Version reporting made more robust
- Path tree allows concurrent updates
- Improved class loading
- Reduced console output
- Significant refactoring to allow for lightweight testing and fuzzing

### Deprecated
- ...

### Removed
- ...

### Fixed
- Travis CI docker setup
- Bug #15: new symbolic character bounds were incorrect (String.java::charAt__I__C) 

## [0.0.1]

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
