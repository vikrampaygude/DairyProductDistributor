
cd UI/DairyProductDistributor
ng build --prod
cd ../../

mvn clean install
git add .
git commit -m 'deploy changes'
git push heroku master