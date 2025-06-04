# Build option
if [ "$4" = "build" ] 
then
    cd Hagimule
    make build
    cd ..
    ssh -i "AWS_$3.pem" admin@$1 'mkdir -p Client Common Diary Input Output && cd Input && dd if=/dev/zero of=zfile bs=1M count=1024 && echo this is a basic file for a simple test > simple.txt'
    scp -ri "AWS_$3.pem" Hagimule/src/Client/*.class admin@$1:Client
    scp -ri "AWS_$3.pem" Hagimule/src/Common/*.class admin@$1:Common
    scp -ri "AWS_$3.pem" Hagimule/src/Diary/*.class admin@$1:Diary
fi

echo "=============================================================================================="
ssh -i "AWS_$3.pem" admin@$1 'java Client.Client "'$2'"'
ssh -i "AWS_$3.pem" admin@$1 'killall java'
