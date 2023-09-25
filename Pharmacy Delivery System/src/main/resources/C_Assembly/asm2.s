.section .data   

.section .text

        .global cal_min

cal_min:

        # Prologue
        pushl %ebp
        movl %esp, %ebp
        pushl %ebx
        # considerando que cada ponto de carga tem uma potência disponível (e fixa) de 1 kW e a capacidade da bateria de 1 kWh:

        movl $0, %eax
        movl $0, %ebx
        movl $0, %ecx
        movl $0, %edx

        movl 8(%ebp), %eax # bateria em percentagem 
        movl 12(%ebp), %edx # capacidade da bateria
        movl 16(%ebp), %ebx # potencia do carregador
        movl $100, %ecx

        subl %eax, %ecx # determina a bateria que resta carregar em percentagem (ecx)

        movl %edx, %eax

        movl $100, %edx

        mull %edx

        cmpl $0, %ebx
        jz end
        divl %ebx

        mull %ecx

        movl $10000, %ebx

        cdq
        divl %ebx

        movl %edx, %eax

        movl $60, %ebx

        mull %ebx

        movl $10000, %ebx

        divl %ebx
end:
        # Epilogue
        popl %ebx
        movl %ebp, %esp
        popl %ebp
        ret
