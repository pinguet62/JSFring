FROM nginx:1.13.7-alpine

RUN rm -rf /usr/share/nginx/html/*

COPY ./dist /usr/share/nginx/html

CMD ["nginx", "-g", "daemon off;"]
