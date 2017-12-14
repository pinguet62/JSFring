/** Generate configuration file from environment variables. */

const constantes = [
  {configKey: 'api', envName: 'jsfring.webservice.url', default: `window.location.origin.replace('jsfring-webapp-angular', 'jsfring-webservice')`},
]

let lines = []
lines.push('export const environment = {  \n')
for (let constant of constantes) {
  let value = process.env[constant.envName]
  value = value ? `'` + value + `'` : constant.default
  lines.push('    ' + constant.configKey + ': ' + value + ',\n')
}
lines.push('    production: false\n')
lines.push('};')
let content = lines.join('')

let fs = require('fs')
fs.writeFile('./src/environments/environment.ts', content)
