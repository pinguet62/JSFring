FROM nginx:1.13.7-alpine

RUN rm -rf /usr/share/nginx/html/*

COPY ./dist /usr/share/nginx/html

CMD \
    echo $JSFRING_WEBAPP_ANGULAR_CONFIG > /usr/share/nginx/html/assets/config.json && \
    # Default nginx CMD
    /usr/sbin/nginx -g "daemon off;"
