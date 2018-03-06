FROM node:10-alpine

COPY . /opt/jsfring/webapp-angularjs
RUN rm -rf /opt/jsfring/webapp-angularjs/target

WORKDIR /opt/jsfring/webapp-angularjs

CMD ["npm", "start"]
