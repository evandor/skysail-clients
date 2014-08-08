#!/bin/bash

function checkProceeding(){
  if [ $1 -ne 0 ]; then
    echo 1>&2 "$1"
    exit 1
  fi
}

usage(){
    echo "Usage: $0 <bundlename> <version>"
    exit 1
}

if [ $# -lt 2 ]; then
  usage
elif [ $# -gt 2 ]; then
  usage
  exit 2
fi

cd ..

echo ""
echo "executing 'gradle clean build'"
echo "=============================="
gradle clean build
checkProceeding $? "gradle problem, stopping execution"

echo ""
echo "executing git add -A && git commit -m 'preparing release $1 $2'"
echo "=============================="
git add -A
git commit -m "preparing release $1 $2"
checkProceeding $? "git problem, stopping execution"

echo ""
echo "executing git tag -a $1-$2 -m '$1 $2 tag'"
echo "=============================="
git tag -a $1-$2 -m "$1 $2 tag"
checkProceeding $? "git tag problem, stopping execution"

echo ""
echo "executing 'gradle :$1:release'"
echo "=============================="
gradle :$1:release
checkProceeding $? "gradle release problem, stopping execution"

echo ""
echo "executing git add -A && git commit -m 'releasing $1 $2'"
echo "=============================="
git add -A && git commit -m "releasing $1 $2"
checkProceeding $? "gradle commit problem, stopping execution"

echo ""
echo "executing git push origin $1-$2"
echo "=============================="
git push $1-$2
checkProceeding $? "gradle push problem, stopping execution"

echo ""
echo "executing git push origin develop"
echo "=============================="
git push origin master
checkProceeding $? "gradle push problem, stopping execution"



