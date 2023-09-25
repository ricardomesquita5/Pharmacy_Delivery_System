.section .data   

.section .text

        .global cal_finalMili   

cal_finalMili:

        # Prologue
        pushl %ebp
        movl %esp, %ebp
        pushl %ebx
        pushl %esi

        movl $0, %eax
        movl $0, %ebx
        movl $0, %ecx
        movl $0, %edx

        movl 8(%ebp), %eax # hour
        movl 12(%ebp), %esi # min
        movl 16(%ebp), %ebx # sec

        movl $60, %ecx
        
        cdq
        mull %ecx # passa horas para minutos

        addl %esi, %eax  # soma o total de minutos

        movl $60, %ecx 

        cdq
        mull %ecx # passa min para segundos

        addl %ebx, %eax # soma o total de segundos

        movl $1000, %ecx

        cdq
        mull %ecx # passa segundos para milisec

        # Epilogue
        popl %esi
        popl %ebx
        movl %ebp, %esp
        popl %ebp
        ret
