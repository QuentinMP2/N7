ssh -i "AWS_$2.pem" admin@$1 'wget https://download.oracle.com/java/23/latest/jdk-23_linux-x64_bin.deb && sudo dpkg -i jdk-23_linux-x64_bin.deb && java --version'
