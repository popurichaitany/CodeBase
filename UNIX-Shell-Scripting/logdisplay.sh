exit_condition()
{
        rm ${CURDIR}/match_${C_PID}.txt ${CURDIR}/current_${C_PID}.txt ${CURDIR}/output_${C_PID}.txt ${CURDIR}/target_${C_PID}.csv  ${CURDIR}/format_protocol_list_${C_PID}.txt ${CURDIR}/srch_ftp_${C_PID}.txt 2>/dev/null
        rm ${CURDIR}/${FILENAME} 2> /dev/null
        exit 1
}

ftp_mining()
{
	DAY=`echo ${DT} | cut -c5-6`
	MONTH=`echo ${DT} | cut -c3-4`

	case "$MONTH" in
			01) $MONTH=`echo "Jan"`
						  ;;
			02) $MONTH=`echo "Feb"`
						  ;;
			03) $MONTH=`echo "Mar"`
                                      ;;
			04) $MONTH=`echo "Apr"`
                                      ;;
			05) $MONTH=`echo "May`
						  ;;
			06) $MONTH=`echo "Jun"`
                                      ;;
			07) $MONTH=`echo "Jul"`
						  ;;
			08) $MONTH=`echo "Aug"`
                                      ;;
			09) $MONTH=`echo "Sep"`
						  ;;
			10) $MONTH=`echo "Oct"`
                                      ;;
			11) $MONTH=`echo "Nov"`
                                      ;;
			12) $MONTH=`echo "Dec"`
                                      ;;
	esac
	REFINE_BYDATE=`echo "${MONTH} ${DAY}"`
	grep "${FILENAME}" ${FTP_BASEDIR}/xferlog | grep ${REFINE_BYDATE} 2>/dev/null >> srch_ftp_${C_PID}.txt
	UPLOAD=0 ; DOWNLOAD=0
	if [ -s ${CURDIR}/srch_ftp_${C_PID}.txt ]
	then
		for activity in `cat ${CURDIR}/srch_ftp_${C_PID}.txt`
		do
			DTR=`echo "${activity}" | cut -d' ' -f12`
			if [ ${DTR} = "o" ]
			then
				let UPLOAD++
			elif [ ${DTR} = "i" ]
			then
				let DOWNLOAD++
			fi
		done
					
		if [ ${UPLOAD} -gt 0 ]
		then
			echo "File $FILENAME is uploaded $UPLOAD time(s) on ${DT} (yymmdd) through FTP"
		fi
		if [ ${DOWNLOAD} -gt 0 ]
		then
			echo "File $FILENAME is downloaded $DOWNLOAD time(s) on ${DT} (yymmdd) through FTP"
		fi					
	fi
	cat ${CURDIR}/srch_ftp_${C_PID}.txt
	rm ${CURDIR}/srch_ftp_${C_PID}.txt
}
xfb_mining()
{
	while read ENTRIES
	do
       	FORMAT=`echo $ENTRIES | cut -d',' -f2`
       	LOGPATH=`echo $ENTRIES | cut -d',' -f3`
       	LOG=`echo $ENTRIES | cut -d',' -f4`
	
		ls -lart $FORMAT 2> /dev/null | awk '{ print $9 }'> ${CURDIR}/match_${C_PID}.txt
		if [ -s ${CURDIR}/match_${C_PID}.txt ]
		then
			cd $LOGPATH
			ls -lart *${DT}.log 2> /dev/null | awk '{ print $9 }'> ${CURDIR}/current_${C_PID}.txt
			if [ -s ${CURDIR}/current_${C_PID}.txt ]
			then
				while read LOG
				do
                
					START=0 ; STOP=0 ; FOUND=0 
                   
					cat ${logf} | while read LINE 
					do
						flag1=0 ; flag2=0 ; flag3=0 ; flag4=0
 
						STR1=`echo "${LINE}" | grep "START"`
						STR5=`echo "${LINE}" | grep "STOP"`
						STR2=`echo "${LINE}" | grep "${FILENAME}"`
						if [ "X${STR2}" != "X" ]
						then
							FOUND=1
						fi
						STR3=`echo "${LINE}" | grep -E [0-9]{2}/[0-9]{2}/[0-9]{2}`
						STR4=`echo "${LINE}" | grep -E [0-9]{2}:[0-9]{2}:[0-9]{2}`                       
                      
						if [ "X${STR1}" != "X" ]
						then
							flag1=1
						fi
						if [ "X${STR5}" = "X" ]
						then
							flag4=1
						fi
						if [ "X${STR3}" != "X" ]
						then
							flag2=1
						fi
						if [ "X${STR4}" != "X" ]
						then
							flag3=1
						fi
                      
						if [ "${flag2}" -eq 1 ] && [ "${flag3}" -eq 1 ] && [ "${flag1}" -eq 1 ] 
						then
							echo "${LINE}" >> ${CURDIR}/output_${C_PID}.txt
							let START++
						elif [ "${flag2}" -eq 1 ] && [ "${flag3}" -eq 1 ] && [ "${flag4}" -eq 1 ] 
						then
							echo "${LINE}" >> ${CURDIR}/output_${C_PID}.txt 
							let STOP++
						else
							echo "${LINE}" >> ${CURDIR}/output_${C_PID}.txt
						fi                          
                    
						if [ "${FOUND}" -eq 1 ] && [ "${START}" == "${STOP}" ] 
						then
							cat ${CURDIR}/output_${C_PID}.txt 2>/dev/null
							rm ${CURDIR}/output_${C_PID}.txt 2>/dev/null
						fi
					done  
				done < ${CURDIR}/current_${C_PID}.txt
			fi
		fi
	done < ${CURDIR}/target_${C_PID}.csv
	exit_condition
}
sftp_mining()
{
	STREAM_NAME=$1
	TARGET_FILE=`echo "20${DT}.transfer.*"`
	LOG_TERM_EXPR=`echo "[a-z]+"`
	LOG_SRCH_STR="SFTP PU(SH|LL) for ${FILENAME}"
	
	LOGPATH=`grep "LOGHOME" /deds-host/olos/dedsfiles/${STREAM_NAME}/*control | tr -s " " | cut -d' ' -f2`
	ls -lart ${LOGPATH}/${TARGET_FILE} 2>/dev/null | tr -s " " | cut -f9 >> ${CURDIR}/target_${C_PID}.txt
	if [ -s ${CURDIR}/target_${C_PID}.txt ]
	then
		while read FILES
		do
			grep -nE "${LOG_SRCH_STR}" ${FILES} >> ${CURDIR}/line_pointer_${C_PID}.txt
				
			while read LINES
			do
				LN=`echo "${LINES}" | cut -d':' -f1`
				LOGGING=1
						
				while [ ${LOGGING} == "1" ]
				do
					OUTPUT_LINE=`sed -n '${LN}'p ${FILENAME}`
					echo "${OUTPUT_LINE}" 
	
					LOG_COMPLETE=""
					LOG_COMPLETE=`echo ${LINES} | grep -E "${LOG_TERM_EXPR}" 2>/dev/null`
	
					if [ "X${LOG_COMPLETE}" = "X" ]
					then
						LOGGING=0
					fi
					let LN++
				done 
							
			done < ${CURDIR}/line_pointer_${C_PID}.txt
		done < ${CURDIR}/target_${C_PID}.txt
	else
		echo "Stream ${STREAM_NAME} was not activated to receive the file on SDEDS through SFTP on 20${DT} (YYYYMMDD)"
	fi
	rm ${CURDIR}/target_${C_PID}.txt 2> /dev/null
}
CURDIR=/deds-host/olos/Anand
SFTP_BASEDIR=/deds-host/logs/ta-app
FTP_BASEDIR=/var/adm
# checking parameters
if [ $# -lt 2 ]
then
        echo "`date` : ERROR : Missing Arguements. Please enter FILENAME and DATE(yymmdd) as parameter." 
        exit_condition
else
        FILENAME=$1 ; DT=$2 ; STORY=$3 ;
        touch ${CURDIR}/${FILENAME}
fi
C_PID=$$
VALID_DATE=`echo "${DT}" | grep -E "[0-9]{6}"`
DAY=`echo ${DT} | cut -c5-6`
MONTH=`echo ${DT} | cut -c3-4`
if [ DAY >= 1 ] && [ DAY <=31 ] && [ MONTH >= 1 ] && [ MONTH <= 12 ]
then
	RANGE_FLAG=1
fi
if [ "X${VALID_DATE}" = "X" ] || [ "${RANGE_FLAG}" == 0 ]
then
	echo "`date` : ERROR : Invalid date input, cannot proceed."
	exit_condition
fi
cd ${CURDIR}
LOG=""
SRCH_CHAR=`echo "${FILENAME}" | cut -c1`
	 
FLAG1=0
if [ -n ${STORY} ]
then
	FLAG2=`echo "${STORY}" | grep -E "DEDS-[0-9]{1,2,3,4}"`
	FLAG3=`echo "${STORY}" | grep -E "CE*"`
	if [ -n ${FLAG2} ] || [ -n ${FLAG3} ]
	then
		grep "${STORY}" ${CURDIR}/IVVT.csv >> ${CURDIR}/target_${C_PID}.csv
		FLAG1=1
	else
		echo " Invalid story name is input"
	fi
fi
	
if [ FLAG1 -eq 0 ]
then
	FLAG4=`echo "${SRCH_CHAR}" | grep -E "[0-9]{1}"`
	if [ "X${FLAG4}" != "X" ] 
	then
		grep ",?" ${CURDIR}/IVVT.csv >> ${CURDIR}/target_${C_PID}.csv
	else
		grep  "[0-9],${SRCH_CHAR}" ${CURDIR}/IVVT.csv >> ${CURDIR}/target_${C_PID}.csv
	fi
fi
	
cat ${CURDIR}/target_${C_PID}.csv | cut -d',' -f2,5,6 2>/dev/null >> ${CURDIR}/format_protocol_list_${C_PID}.txt			
while read entries
do
	fle=`echo ${entries} | cut -d',' -f1`
	fle_match=`ls -lart ${fle} | tr -s " " | cut -d' ' -f9`
	if [ "${fle_match}" == "${FILENAME}" ]
	then
		PROTO=`echo ${entries} | cut -d',' -f2`
		if [ ${PROTO} != "-" ]
		then
			if [ ${PROTO} = "FTP" ] || [ ${PROTO} = "FTPS" ]
			then
				ftp_mining
				xfb_mining
			elif [ ${PROTO} = "SFTP" ]
			then
				stream=`echo ${entries} | cut -d',' -f3`
				sftp_mining ${stream}
				cd ${CURDIR} 
				xfb_mining
			fi
		else
			xfb_mining
		fi
	fi
done < ${CURDIR}/format_protocol_list_${C_PID}.txt

    S
