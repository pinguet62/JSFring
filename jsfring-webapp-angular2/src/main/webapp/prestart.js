/** Generate "./app/environment.ts" file from environment variables. */

var constantes = [
    { name: 'api', key: 'jsfring.angular.url'},
];

var lines = [];
lines.push('export const environment = {  \n');
for (let constant of constantes)
    lines.push('    ' + constant.name + ': \'' + process.env[constant.key] + '\',\n');
lines.push('};');
var content = lines.join("");

var fs = require('fs');
fs.writeFile('app/environment.ts', content);
