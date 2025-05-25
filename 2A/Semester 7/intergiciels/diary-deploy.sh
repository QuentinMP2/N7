# Build option
if [ "$2" = "build" ] 
then
    cd Hagimule
    make build
    cd ..
    ssh -i "AWS_Singapour.pem" admin@$1 'mkdir -p Diary Common'
    scp -ri "AWS_Singapour.pem" Hagimule/src/Diary/*.class admin@$1:Diary
    scp -ri "AWS_Singapour.pem" Hagimule/src/Common/*.class admin@$1:Common
fi

echo "=============================================================================================="
ssh -i "AWS_Singapour.pem" admin@$1 'java -Djava.rmi.server.hostname='$1' Diary.AnnuaireImpl "0.0.0.0"'
ssh -i "AWS_Singapour.pem" admin@$1 'killall java'
