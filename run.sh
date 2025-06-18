#!/bin/bash

# Scala-playground project execution script

# Show usage information
show_usage() {
    echo "Usage: $0 [COMMAND]"
    echo ""
    echo "Commands:"
    echo "  run               - Run the project"
    echo "  test              - Run all tests"
    echo "  test-watch        - Run tests automatically when files change"
    echo "  test-only <class> - Run specific test class only"
    echo "  test-compile      - Compile tests only"
    echo "  clean             - Clean build files"
    echo "  compile           - Compile the project"
    echo "  help              - Show this help"
    echo ""
    echo "Examples:"
    echo "  $0 test"
    echo "  $0 test-only MySuite"
    echo "  $0 test-only examples.DependencyInjectionExampleTest"
}

# Test execution function
run_tests() {
    echo "🧪 Running all tests..."
    sbt test
}

# Run specific test class
run_test_only() {
    if [ -z "$1" ]; then
        echo "❌ Error: Please specify a test class name"
        echo "Example: $0 test-only MySuite"
        exit 1
    fi
    echo "🎯 Running test class '$1'..."
    sbt "testOnly $1"
}

# Run tests in file watch mode
run_test_watch() {
    echo "👀 Running tests in file watch mode..."
    echo "Tests will run automatically when files change. Press Ctrl+C to exit."
    sbt ~test
}

# Compile tests only
compile_tests() {
    echo "🔨 Compiling tests..."
    sbt Test/compile
}

# Run project
run_project() {
    echo "🚀 Running project..."
    sbt run
}

# Clean project
clean_project() {
    echo "🧹 Cleaning project..."
    sbt clean
}

# Compile project
compile_project() {
    echo "🔨 Compiling project..."
    sbt compile
}

# Main processing
case "${1:-run}" in
    "run")
        run_project
        ;;
    "test")
        run_tests
        ;;
    "test-watch")
        run_test_watch
        ;;
    "test-only")
        run_test_only "$2"
        ;;
    "test-compile")
        compile_tests
        ;;
    "clean")
        clean_project
        ;;
    "compile")
        compile_project
        ;;
    "help"|"-h"|"--help")
        show_usage
        ;;
    *)
        echo "❌ Unknown command: $1"
        echo ""
        show_usage
        exit 1
        ;;
esac
