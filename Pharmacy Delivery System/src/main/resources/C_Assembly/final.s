.section .data   

.section .text

        .global final  

final:

        # Prologue
        pushl %ebp
        movl %esp, %ebp

        movl $0, %eax
        movl $0, %ecx

        movl 8(%ebp), %ecx
        movl 12(%ebp), %eax

        addl %ecx, %eax

        # Epilogue
        movl %ebp, %esp
        popl %ebp
        ret
