

for dir in */; do 

cd  $dir; 
git pull
mvn clean  -DskipTests  $*
cd ..

done
 
