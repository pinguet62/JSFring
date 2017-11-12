import os
import re
import sys

# cd to this folder
liquibaseFolder = os.path.dirname(sys.argv[0])
os.chdir(liquibaseFolder)

subChangelogs = sorted([filename for filename in os.listdir('changelogs') if re.match('.*\.yaml$', filename)])
includes = ['  - include: { file: src/main/resources/changelogs/' + subChangelog + ' }\n' for subChangelog in subChangelogs]
with open('changelog-master.yaml', 'w') as changelogFile:
    changelogFile.write('# Auto-generated file. Crushed during build.\n')
    changelogFile.write('databaseChangeLog:\n')
    changelogFile.writelines(includes)
