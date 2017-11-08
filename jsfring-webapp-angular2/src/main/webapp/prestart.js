/** Generate configuration file from environment variables. */

const constantes = [
    { configKey: 'api', envName: 'jsfring.angular.url'},
];

let lines = [];
lines.push('export const environment = {  \n');
for (let constant of constantes)
    lines.push('    ' + constant.configKey + ': \'' + process.env[constant.envName] + '\',\n');
lines.push('    production: false\n');
lines.push('};');
let content = lines.join("");

let fs = require('fs');
fs.writeFile('./src/environments/environment.ts', content);
