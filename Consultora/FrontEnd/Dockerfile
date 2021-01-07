FROM node:12.16.1 as build-env
LABEL desc="docker image of angular app"
WORKDIR /app
COPY ["package.json","package-lock.json","/app/"]
RUN npm install
RUN npm install -g @angular/cli

COPY . /app
#ENTRYPOINT ["ng","--version"]
RUN ng build
CMD ng serve --host 0.0.0.0 --port 4200