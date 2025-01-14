#!/bin/bash
if [ $# -ne 1 ]; then
  echo "Usage: $0 <JavaFile.java>"
  exit 1
fi

java_file=$1
class_name=$(basename "$java_file" .java)

clear && javac "$java_file" && java "$class_name"

# remove the compiled class file
rm -rf *.class