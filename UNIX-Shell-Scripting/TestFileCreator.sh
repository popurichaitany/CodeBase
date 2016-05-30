#! /bin/ksh

#########################################################################
#       Script          : filecreator.sh                                #
#                                                                       #
#       Description     : ./filecreator.sh <No. of file> <SIZEunit>     #
#                                                                       #
#       Version 1.0     14-06-2013   Anand Kamathi                      #
#                                                                       #
#########################################################################
checkproccessrunning()
{
        PROCRUNNING=`/deds-host/olos/VDC/check_proc_vdc.sh $1`

        if [ $PROCRUNNING -eq 1 ]
        then
                echo "`date` : ERROR : Process for ${script} already running. Aborting this instance."
                exit 1
        fi
}

exit_condition()
{
        exit 1
}

script=`basename $0`

if [ $# -ne 2 ]
then
        echo "`date` : ERROR : Missing Arguements. Please enter number of file and size of file with unit as parameter."
        exit_condition
else
        FNUM=$1
        SIZE=$2
fi

checkproccessrunning ${script}
COUNT=1

cd /deds-host/olos/Anand

KBREQUEST=`echo "${SIZE}" | grep -E "*K"`
MBREQUEST=`echo "${SIZE}" | grep -E "*M"`

if [ "X${KBREQUEST}" != "X" ]
then
        export INPUTFILE=/deds-host/olos/Anand/imp/output1.txt
elif [ "X${MBREQUEST}" != "X" ]
then
        export INPUTFILE=/deds-host/olos/Anand/imp/output2.txt
else
        echo "Unit of size need to be input."
        exit_condition
fi

SIZE_LENGTH=`echo "${SIZE}" | wc -c`
REQ_LENGTH=`expr ${SIZE_LENGTH} - 2`
SIZE=${SIZE:0:${REQ_LENGTH}}

while [ "${COUNT}" -le "${FNUM}" ]
do
        NUM=0
        while [ "${NUM}" != "${SIZE}" ]
        do
                while read LINE
                do
                        echo "${LINE}" >> /deds-host/olos/Anand/output${COUNT}.txt 2>/dev/null
                done < ${INPUTFILE}

                let NUM++
        done
        if [ -f /deds-host/olos/Anand/output${COUNT}.txt ]
        then
                echo "Enter name for file output${COUNT}:"
                read NAME
                mv /deds-host/olos/Anand/output${COUNT}.txt /deds-host/olos/Anand/${NAME}
        else
                echo "Output file output${COUNT}.txt not found at /deds-host/olos/Anand"
        fi
        let COUNT++
done

exit_condition
