/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 *
 * CompactRegressionTree.h
 *
 * Code generation for function 'CompactRegressionTree'
 *
 */

#ifndef COMPACTREGRESSIONTREE_H
#define COMPACTREGRESSIONTREE_H

/* Include files */
#include "predict_internal_types.h"
#include "rtwtypes.h"
#include <stddef.h>
#include <stdlib.h>

#ifdef __cplusplus
extern "C" {
#endif

/* Function Declarations */
double CompactRegressionTree_predict(const double obj_CutPredictorIndex[681],
                                     const double obj_Children[1362],
                                     const double obj_CutPoint[681],
                                     const boolean_T obj_NanCutPoints[681],
                                     const double obj_NodeMean[681],
                                     const double Xin[6]);

double b_CompactRegressionTree_predict(const double obj_CutPredictorIndex[569],
                                       const double obj_Children[1138],
                                       const double obj_CutPoint[569],
                                       const boolean_T obj_NanCutPoints[569],
                                       const double obj_NodeMean[569],
                                       const double Xin[6]);

void c_CompactRegressionTree_Compact(
    double obj_CutPredictorIndex[559], double obj_Children[1118],
    double obj_CutPoint[559], boolean_T obj_NanCutPoints[559],
    boolean_T obj_InfCutPoints[559],
    c_classreg_learning_coderutils_ *obj_ResponseTransform,
    double obj_NodeMean[559]);

double c_CompactRegressionTree_predict(const double obj_CutPredictorIndex[643],
                                       const double obj_Children[1286],
                                       const double obj_CutPoint[643],
                                       const boolean_T obj_NanCutPoints[643],
                                       const double obj_NodeMean[643],
                                       const double Xin[6]);

void d_CompactRegressionTree_Compact(
    double obj_CutPredictorIndex[585], double obj_Children[1170],
    double obj_CutPoint[585], boolean_T obj_NanCutPoints[585],
    boolean_T obj_InfCutPoints[585],
    c_classreg_learning_coderutils_ *obj_ResponseTransform,
    double obj_NodeMean[585]);

double d_CompactRegressionTree_predict(const double obj_CutPredictorIndex[659],
                                       const double obj_Children[1318],
                                       const double obj_CutPoint[659],
                                       const boolean_T obj_NanCutPoints[659],
                                       const double obj_NodeMean[659],
                                       const double Xin[6]);

void e_CompactRegressionTree_Compact(
    double obj_CutPredictorIndex[609], double obj_Children[1218],
    double obj_CutPoint[609], boolean_T obj_NanCutPoints[609],
    boolean_T obj_InfCutPoints[609],
    c_classreg_learning_coderutils_ *obj_ResponseTransform,
    double obj_NodeMean[609]);

double e_CompactRegressionTree_predict(const double obj_CutPredictorIndex[621],
                                       const double obj_Children[1242],
                                       const double obj_CutPoint[621],
                                       const boolean_T obj_NanCutPoints[621],
                                       const double obj_NodeMean[621],
                                       const double Xin[6]);

double f_CompactRegressionTree_predict(const double obj_CutPredictorIndex[597],
                                       const double obj_Children[1194],
                                       const double obj_CutPoint[597],
                                       const boolean_T obj_NanCutPoints[597],
                                       const double obj_NodeMean[597],
                                       const double Xin[6]);

double g_CompactRegressionTree_predict(const double obj_CutPredictorIndex[625],
                                       const double obj_Children[1250],
                                       const double obj_CutPoint[625],
                                       const boolean_T obj_NanCutPoints[625],
                                       const double obj_NodeMean[625],
                                       const double Xin[6]);

double h_CompactRegressionTree_predict(const double obj_CutPredictorIndex[673],
                                       const double obj_Children[1346],
                                       const double obj_CutPoint[673],
                                       const boolean_T obj_NanCutPoints[673],
                                       const double obj_NodeMean[673],
                                       const double Xin[6]);

double i_CompactRegressionTree_predict(const double obj_CutPredictorIndex[623],
                                       const double obj_Children[1246],
                                       const double obj_CutPoint[623],
                                       const boolean_T obj_NanCutPoints[623],
                                       const double obj_NodeMean[623],
                                       const double Xin[6]);

double j_CompactRegressionTree_predict(const double obj_CutPredictorIndex[661],
                                       const double obj_Children[1322],
                                       const double obj_CutPoint[661],
                                       const boolean_T obj_NanCutPoints[661],
                                       const double obj_NodeMean[661],
                                       const double Xin[6]);

double k_CompactRegressionTree_predict(const double obj_CutPredictorIndex[655],
                                       const double obj_Children[1310],
                                       const double obj_CutPoint[655],
                                       const boolean_T obj_NanCutPoints[655],
                                       const double obj_NodeMean[655],
                                       const double Xin[6]);

double l_CompactRegressionTree_predict(const double obj_CutPredictorIndex[677],
                                       const double obj_Children[1354],
                                       const double obj_CutPoint[677],
                                       const boolean_T obj_NanCutPoints[677],
                                       const double obj_NodeMean[677],
                                       const double Xin[6]);

double m_CompactRegressionTree_predict(const double obj_CutPredictorIndex[635],
                                       const double obj_Children[1270],
                                       const double obj_CutPoint[635],
                                       const boolean_T obj_NanCutPoints[635],
                                       const double obj_NodeMean[635],
                                       const double Xin[6]);

double n_CompactRegressionTree_predict(const double obj_CutPredictorIndex[617],
                                       const double obj_Children[1234],
                                       const double obj_CutPoint[617],
                                       const boolean_T obj_NanCutPoints[617],
                                       const double obj_NodeMean[617],
                                       const double Xin[6]);

double o_CompactRegressionTree_predict(const double obj_CutPredictorIndex[609],
                                       const double obj_Children[1218],
                                       const double obj_CutPoint[609],
                                       const boolean_T obj_NanCutPoints[609],
                                       const double obj_NodeMean[609],
                                       const double Xin[6]);

double p_CompactRegressionTree_predict(const double obj_CutPredictorIndex[611],
                                       const double obj_Children[1222],
                                       const double obj_CutPoint[611],
                                       const boolean_T obj_NanCutPoints[611],
                                       const double obj_NodeMean[611],
                                       const double Xin[6]);

double q_CompactRegressionTree_predict(const double obj_CutPredictorIndex[663],
                                       const double obj_Children[1326],
                                       const double obj_CutPoint[663],
                                       const boolean_T obj_NanCutPoints[663],
                                       const double obj_NodeMean[663],
                                       const double Xin[6]);

double r_CompactRegressionTree_predict(const double obj_CutPredictorIndex[657],
                                       const double obj_Children[1314],
                                       const double obj_CutPoint[657],
                                       const boolean_T obj_NanCutPoints[657],
                                       const double obj_NodeMean[657],
                                       const double Xin[6]);

double s_CompactRegressionTree_predict(const double obj_CutPredictorIndex[593],
                                       const double obj_Children[1186],
                                       const double obj_CutPoint[593],
                                       const boolean_T obj_NanCutPoints[593],
                                       const double obj_NodeMean[593],
                                       const double Xin[6]);

double t_CompactRegressionTree_predict(const double obj_CutPredictorIndex[559],
                                       const double obj_Children[1118],
                                       const double obj_CutPoint[559],
                                       const boolean_T obj_NanCutPoints[559],
                                       const double obj_NodeMean[559],
                                       const double Xin[6]);

double u_CompactRegressionTree_predict(const double obj_CutPredictorIndex[585],
                                       const double obj_Children[1170],
                                       const double obj_CutPoint[585],
                                       const boolean_T obj_NanCutPoints[585],
                                       const double obj_NodeMean[585],
                                       const double Xin[6]);

#ifdef __cplusplus
}
#endif

#endif
/* End of code generation (CompactRegressionTree.h) */
