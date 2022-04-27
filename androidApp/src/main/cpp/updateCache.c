/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 *
 * updateCache.c
 *
 * Code generation for function 'updateCache'
 *
 */

/* Include files */
#include "updateCache.h"
#include "rt_nonfinite.h"

/* Function Definitions */
double updateCache(double learnerscore, double *cachedScore,
                   double *cachedWeights, boolean_T *cached,
                   const char combinerName[15])
{
  static const char cv[128] = {
      '\x00',  '\x01', '\x02', '\x03', '\x04', '\x05', '\x06', '\x07', '\x08',
      '	', '\x0a', '\x0b', '\x0c', '\x0d', '\x0e', '\x0f', '\x10', '\x11',
      '\x12',  '\x13', '\x14', '\x15', '\x16', '\x17', '\x18', '\x19', '\x1a',
      '\x1b',  '\x1c', '\x1d', '\x1e', '\x1f', ' ',    '!',    '\"',   '#',
      '$',     '%',    '&',    '\'',   '(',    ')',    '*',    '+',    ',',
      '-',     '.',    '/',    '0',    '1',    '2',    '3',    '4',    '5',
      '6',     '7',    '8',    '9',    ':',    ';',    '<',    '=',    '>',
      '?',     '@',    'a',    'b',    'c',    'd',    'e',    'f',    'g',
      'h',     'i',    'j',    'k',    'l',    'm',    'n',    'o',    'p',
      'q',     'r',    's',    't',    'u',    'v',    'w',    'x',    'y',
      'z',     '[',    '\\',   ']',    '^',    '_',    '`',    'a',    'b',
      'c',     'd',    'e',    'f',    'g',    'h',    'i',    'j',    'k',
      'l',     'm',    'n',    'o',    'p',    'q',    'r',    's',    't',
      'u',     'v',    'w',    'x',    'y',    'z',    '{',    '|',    '}',
      '~',     '\x7f'};
  static const char cv1[15] = {'w', 'e', 'i', 'g', 'h', 't', 'e', 'd',
                               'a', 'v', 'e', 'r', 'a', 'g', 'e'};
  double score;
  int exitg1;
  int kstr;
  boolean_T b_bool;
  score = *cachedScore;
  if (!*cached) {
    *cached = true;
    *cachedScore += learnerscore;
    (*cachedWeights)++;
    b_bool = false;
    kstr = 0;
    do {
      exitg1 = 0;
      if (kstr < 15) {
        if (cv[(unsigned char)combinerName[kstr]] != cv[(int)cv1[kstr]]) {
          exitg1 = 1;
        } else {
          kstr++;
        }
      } else {
        b_bool = true;
        exitg1 = 1;
      }
    } while (exitg1 == 0);
    if (b_bool) {
      score = *cachedScore / *cachedWeights;
    } else {
      score = *cachedScore;
    }
  }
  return score;
}

/* End of code generation (updateCache.c) */
