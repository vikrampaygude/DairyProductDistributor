
cd UI/DairyProductDistributor
ng build --prod
cd ../../

git add .
git commit -m 'deploy changes'
mvn clean install
git push heroku master