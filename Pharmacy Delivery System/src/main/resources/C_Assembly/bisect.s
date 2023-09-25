.section .data   

.section .text

        .global check_bisect 

check_bisect:

        # Prologue
        pushl %ebp
        movl %esp, %ebp

        movl $0, %eax
        movl $0, %ecx

        movl 8(%ebp), %eax
        movl $4, %ecx

        cdq
        divl %ecx

        movl %edx, %eax

        # Epilogue
        movl %ebp, %esp
        popl %ebp
        ret
