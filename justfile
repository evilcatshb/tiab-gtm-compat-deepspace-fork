build:
  ./gradlew build

run-client:
  ./gradlew runClient

run-server:
  ./gradlew runServer

run-data:
  ./gradlew runData

clean:
  ./gradlew clean

publish:
  ./gradlew build publishMods

release version:
  sed -i "s/^mod_version=.*/mod_version={{version}}/" gradle.properties
  git add gradle.properties CHANGELOG.md
  git commit -m "chore: release v{{version}}"
  git tag -a "v{{version}}" -m "v{{version}}"
  git push origin main --tags
