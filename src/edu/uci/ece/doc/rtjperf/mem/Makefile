all:
	javac -classpath /home/zeus/Tools/RTJava/lib/foundation.jar:/home/zeus/Devel/RTJPerf/classes/  AllocTimeTest.java

run:
	/home/zeus/Tools/RTJava/bin/tjvm  -Xbootclasspath=/home/zeus/Tools/RTJava/lib/foundation.jar -Djava.library.path=/home/zeus/Devel/RTJPerf/lib -Djava.class.path=./:/home/zeus/Devel/RTJPerf/classes -Dproperties.load.path=$RTJP_HOME/bin AllocTimeTest 10000 1
