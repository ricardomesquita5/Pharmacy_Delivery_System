.section .data   

.section .text

        .global cal_spot

cal_spot:

        # Prologue
        pushl %ebp
        movl %esp, %ebp

        movl $0, %eax
        movl $0, %ecx

        movl 8(%ebp), %eax
        movl 12(%ebp), %ecx

        cmpl $0, %ecx
        jz end

        cdq
        divl %ecx

end:
        # Epilogue
        movl %ebp, %esp
        popl %ebp
        ret
