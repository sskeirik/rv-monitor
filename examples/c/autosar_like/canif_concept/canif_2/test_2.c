#include "../CanIf.h"
#include <stdio.h>

int main( int argc, const char* argv[] )
{
    CanIf_Init();
    CanIf_GetControllerMode(5);
    CanIf_Transmit();
    CanIf_Transmit();
}
