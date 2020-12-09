#!/usr/bin/env sh

SONAR_HOST=http://101.132.102.201:9000

analysis() {
  cd "$1" && mvn clean verify sonar:sonar \
    -Dsonar.projectKey=org.NJU-SE-17-advanced-se:"$1" \
    -Dsonar.host.url="$SONAR_HOST" \
    -Dsonar.login=$2
}

if [ "$1" = 'entity-affiliation' ]
then
  analysis "$1" fc6226328cbb9efeaddf7b3c002cd46c946e5361
elif [ "$1" = 'entity-domain' ]
then
  analysis "$1" fec90eef58759c523e107ea87c9731c84e035465
elif [ "$1" = 'entity-paper' ]
then
  analysis "$1" e328bf8559595ec65b4619aaff963fb23eb043aa
elif [ "$1" = 'entity-publication' ]
then
  analysis "$1" 50ae0cd7fe262db7127c47a824e2d5fbda68be9a
elif [ "$1" = 'entity-researcher' ]
then
  analysis "$1" f2fe9c0e95704d3735f3344e0eb540f102d4e3c0
elif [ "$1" = 'task-citation-analysis' ]
then
  analysis "$1" 68dfcd2399a49d8d2322a818dc12e5fb81b9d508
elif [ "$1" = 'task-domain-prediction' ]
then
  analysis "$1" 796c57037e34dd7e9ac9b7a7254e90159912f56f
elif [ "$1" = 'task-impact-analysis' ]
then
  analysis "$1" 40a0a728d53daf057af51f5c95db539e35634f9e
elif [ "$1" = 'task-partnership-analysis' ]
then
  analysis "$1" 9ebf0fcbcd1ab66ac3f62f92a236d09d74b51225
elif [ "$1" = 'task-reviewer-recommendation' ]
then
  analysis "$1" 862f8e6fe6d7d91b6d6ad5792b647997d76e44e6
else
  echo 'service not exist'
fi
