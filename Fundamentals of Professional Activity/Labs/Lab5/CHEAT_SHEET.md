# BCOMP / BEVM Quick Cheat Sheet



## 1) Number Basics

- `0x..` means hexadecimal (base 16).
- Hex digits: `0 1 2 3 4 5 6 7 8 9 A B C D E F`.
- `A=10`, `B=11`, `C=12`, `D=13`, `E=14`, `F=15`.
- `ASL` means shift left by 1 bit (same effect as multiply by 2).

## 2) VU Device Ports 

- `VU-9` keypad:
  - status: `IN 0x1D`
  - data: `IN 0x1C`
- `VU-7` seven-segment display:
  - status: `IN 0x15`
  - data write: `OUT 0x14`
- ready bit mask: `0x40`

Polling pattern:

```asm
WAIT_RDY:
    IN 0x1D
    AND READY_MASK
    BEQ WAIT_RDY
    IN 0x1C
```

Output-safe pattern:

```asm
OUT7_WAIT:
    IN 0x15
    AND READY_MASK
    BEQ OUT7_WAIT
    OUT 0x14
```

## 3) VU-7 Output Encoding (Most Important)

The value sent to `OUT 0x14` is packed as:

- high nibble (upper 4 bits): position
- low nibble (lower 4 bits): symbol

Formula:

- `value = (position << 4) | symbol`
- same as `position*16 + symbol`

Examples:

- position 1 -> `0x10`
- position 2 -> `0x20`
- position 3 -> `0x30`
- minus at position 3 -> `0x3A` (`0x30 + 0x0A`)
- blank at position 3 -> `0x3F` (`0x30 + 0x0F`)

In this simulator's seven-segment mapping:

- symbol `A` is rendered as minus (`-`)
- symbol `F` is rendered as blank

## 4) Keypad Codes 

Digits:

- `0..9` -> `0x00..0x09`

Operators / control:

- `-` -> `0x0A` (A)
- `+` -> `0x0B` (B)
- `/` -> `0x0C` (C)
- `*` -> `0x0D` (D)
- `=` -> `0x0F` (F)



## 5)  Program Logic 

State variable:

- `PHASE=0`: entering first operand
- operator pressed -> save `OP`, set `PHASE=1`
- `PHASE=1`: entering second operand
- `=` -> compute

Operand building:

- decimal: `x = x*10 + digit` into `OP1_10`, `OP2_10`
- binary: `x = x*2 + digit` into `OP1_2`, `OP2_2`
- if any digit `>=2`, set `BINERR=1`

Compute:

- `+` -> `RES = OP1_10 + OP2_10`
- `-` -> `RES = OP1_10 - OP2_10`
- `*` -> `RES = OP1_2 AND OP2_2` (or `0` if `BINERR=1`)
- `/` -> `RES = OP1_2 OR OP2_2` (or `0` if `BINERR=1`)

Display:

- set `SIGN` and `ABS`
- split `ABS` into `HUND`, `TENS`, `ONES`
- output pos0, pos1, pos2
- output sign slot (minus/blank) at pos3


## 7) Essential Assembly Syntax

Program skeleton:

```asm
ORG 0x020

LABEL:
    CLA
    LD $VAR
    ADD #1
    ST $VAR
    JUMP $LABEL

VAR: WORD 0
END
```

Core instructions:

- data: `LD`, `ST`, `CLA`
- arithmetic/logic: `ADD`, `SUB`, `AND`, `OR`, `INC`, `DEC`, `NEG`, `ASL`
- compare/branch: `CMP`, `BEQ`, `BNE`, `BLT`, `BPL`, `BR`, `JUMP`
- I/O: `IN`, `OUT`
- subroutine: `CALL`, `RET`

## 8) Most Common Errors and Fast Fixes

1. `label displacement exceed limits [-127..128]`
- Reason: short branch target too far.
- Fix: add intermediate label and use `JUMP` chain.

2. No output on VU-7
- Check correct ports (`0x15` status, `0x14` data).
- Check ready polling before each write.

3. Wrong symbol shown
- Re-check packed value `(position<<4)|symbol`.

4. Weird negatives
- Apply the `SHOW_RES` fix above.

## 9) Ready-to-Use Constants Block

```asm
READY_MASK: WORD 0x40
POS1:       WORD 0x10
POS2:       WORD 0x20
MINUS_P3:   WORD 0x3A
BLANK_P3:   WORD 0x3F
```


