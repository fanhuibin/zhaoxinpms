/*
Navicat MySQL Data Transfer

Source Server         : mysql5.7
Source Server Version : 50733
Source Host           : localhost:3307
Source Database       : pms

Target Server Type    : MYSQL
Target Server Version : 50733
File Encoding         : 65001

Date: 2021-12-11 14:19:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `base_billrule`
-- ----------------------------
DROP TABLE IF EXISTS `base_billrule`;
CREATE TABLE `base_billrule` (
  `Id` varchar(50) NOT NULL COMMENT '自然主键',
  `FullName` varchar(50) DEFAULT NULL COMMENT '单据名称',
  `EnCode` varchar(50) DEFAULT NULL COMMENT '单据编号',
  `Prefix` varchar(50) DEFAULT NULL COMMENT '单据前缀',
  `DateFormat` varchar(50) DEFAULT NULL COMMENT '日期格式',
  `Digit` int(11) DEFAULT NULL COMMENT '流水位数',
  `StartNumber` varchar(50) DEFAULT NULL COMMENT '流水起始',
  `Example` varchar(100) DEFAULT NULL COMMENT '流水范例',
  `ThisNumber` int(11) DEFAULT NULL COMMENT '当前流水号',
  `OutputNumber` varchar(100) DEFAULT NULL COMMENT '输出流水号',
  `Description` longtext COMMENT '描述',
  `SortCode` bigint(20) DEFAULT NULL COMMENT '排序',
  `EnabledMark` int(11) DEFAULT NULL COMMENT '有效标志',
  `CreatorTime` datetime DEFAULT NULL COMMENT '创建时间',
  `CreatorUserId` varchar(50) DEFAULT NULL COMMENT '创建用户',
  `LastModifyTime` datetime DEFAULT NULL COMMENT '修改时间',
  `LastModifyUserId` varchar(50) DEFAULT NULL COMMENT '修改用户',
  `DeleteTime` datetime DEFAULT NULL COMMENT '删除时间',
  `DeleteUserId` varchar(50) DEFAULT NULL COMMENT '删除用户',
  `DeleteMark` int(11) DEFAULT NULL COMMENT '删除标志',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='单据规则';

-- ----------------------------
-- Records of base_billrule
-- ----------------------------
INSERT INTO `base_billrule` VALUES ('013e51fd38724f6c8397aeafc3ed4f44', '流程表单-销售订单流程', 'xiaoshou', 'xiaoshou', 'yyyyMMdd', '2', '03', 'xiaoshou2020110303', '14', 'xiaoshou2021032317', '', '0', '1', '2020-11-03 08:13:17', '03d159a3-0f88-424c-a24f-02f63855fe4f', '2021-03-23 08:54:51', 'admin', null, null, null);
INSERT INTO `base_billrule` VALUES ('05245da9ed2e41808549b1bdfb21313c', '付款流水号', 'pay_no', 'PAY', 'yyyyMMdd', '5', '00200', 'PAY2021090300200', '206', 'PAY2021091800206', '', '0', '1', '2021-09-03 19:05:52', 'admin', '2021-09-18 11:33:13', '1', null, null, null);
INSERT INTO `base_billrule` VALUES ('053853ce9ebc4432893733354277d330', '流程表单-日常物品采购清单', 'WF_PurchaseListNo', 'WF_PAL', 'yyyyMMdd', '4', '0001', 'WF_PAL201807170001', '1', 'WF_PAL202103230002', null, '0', '1', '2018-07-17 17:20:30', 'admin', '2021-03-23 07:41:51', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);
INSERT INTO `base_billrule` VALUES ('06449238365f45c288ed9d1bb382a0d3', '流程表单-发文单', 'WF_LetterServiceNo', 'WF_LTS', 'yyyyMMdd', '4', '0001', 'WF_LTS201807160001', '1', 'WF_LTS202103230002', null, '0', '1', '2018-07-16 16:29:06', 'admin', '2021-03-23 08:04:24', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);
INSERT INTO `base_billrule` VALUES ('0c1a2f41bff44185be1397d631545c7d', '流程表单-发文呈批表', 'WF_PostBatchTabNo', 'WF_PBT', 'yyyyMMdd', '4', '0001', 'WF_PBT201807170001', '2', 'WF_PBT202103230003', null, '0', '1', '2018-07-17 17:17:12', 'admin', '2021-03-23 08:03:57', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);
INSERT INTO `base_billrule` VALUES ('0cfff315d9254724b62cefefe51702ea', '还车申请单', 'huancheshenqing', 'huanche', 'yyyyMMdd', '5', '00001', 'huanche2020110600001', '2', 'huanche2021032300003', '', '0', '1', '2020-11-06 05:58:21', 'admin', '2021-03-23 09:26:00', 'admin', null, null, null);
INSERT INTO `base_billrule` VALUES ('1320485633bc415cb2086f19757584b5', '流程表单-费用支出单', 'WF_ExpenseExpenditureNo', 'WF_EET', 'yyyyMMdd', '4', '0001', 'WF_EET201807160001', '1', 'WF_EET202107230001', null, '0', '1', '2018-07-16 16:25:57', 'admin', '2021-07-23 12:55:34', 'admin', null, null, null);
INSERT INTO `base_billrule` VALUES ('164cc7b942e14e39ad02fade83960a6a', '流程表单-销售支持表', 'WF_SalesSupportNo', 'WF_SLS', 'yyyyMMdd', '4', '0001', 'WF_SLS201807200001', '1', 'WF_SLS202103230002', null, '0', '1', '2018-07-20 09:51:02', 'admin', '2021-03-23 07:35:43', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);
INSERT INTO `base_billrule` VALUES ('17c44f02bc6f4395a3184a167ad6e566', '合同编号', 'hetongbianhao', 'WF_HTBH', 'yyyyMMdd', '4', '0001', 'WF_HTBH202012030001', '3', 'WF_HTBH202103230004', '', '0', '1', '2020-12-03 07:08:35', '0a72e7d8-b519-435b-9c43-6796a31c72ec', '2021-03-23 09:06:05', 'admin', null, null, null);
INSERT INTO `base_billrule` VALUES ('1b77bb2af0964b17989963991778147a', '流程表单-宴请申请', 'WF_ApplyBanquetNo', 'WF_ABQ', 'yyyyMMdd', '4', '0001', 'WF_ABQ201807130001', '1', 'WF_ABQ202103230002', null, '0', '1', '2018-07-12 10:06:29', 'admin', '2021-03-23 07:22:52', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);
INSERT INTO `base_billrule` VALUES ('1c50a8eb13374ca29aafc79434c8a0d0', '流程表单-会议申请', 'WF_ApplyMeetingNo', 'WF_AM', 'yyyyMMdd', '4', '0001', 'WF_AM201807130001', '1', 'WF_AM202103230002', null, '0', '1', '2018-07-13 15:58:52', 'admin', '2021-03-23 07:59:50', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);
INSERT INTO `base_billrule` VALUES ('1ca7952be74043b68bcd529a97995632', '流程表单-付款申请单', 'WF_PaymentApplyNo', 'WF_PAY', 'yyyyMMdd', '4', '0001', 'WF_PAY201807170001', '1', 'WF_PAY202107230001', null, '0', '1', '2018-07-17 17:16:29', 'admin', '2021-07-23 12:55:37', 'admin', null, null, null);
INSERT INTO `base_billrule` VALUES ('28805745ff164ad1b6e56134f3e2ef64', '采购单据', 'cgdj', 'cgdj', 'yyyyMMdd', '8', '00000001', 'cgdj2020111300000001', '2', 'cgdj2021011800000002', '', '0', '1', '2020-11-13 08:14:23', 'admin', '2021-01-18 02:12:44', 'admin', null, null, null);
INSERT INTO `base_billrule` VALUES ('2b92220b13084342a0f48ad42b58c695', '流程表单-收文处理表', 'WF_ReceiptProcessingNo', 'WF_RPC', 'yyyyMMdd', '4', '0001', 'WF_RPC201807170001', '1', 'WF_RPC202103230002', null, '0', '1', '2018-07-17 17:21:21', 'admin', '2021-03-23 08:03:37', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);
INSERT INTO `base_billrule` VALUES ('2dfb20e4961145049fad31390de4fe07', '流程表单-还车申请', 'haiche', 'haiche', 'yyyyMMdd', '2', '01', 'haiche2020110301', '1', 'haiche2020110601', '', '0', '1', '2020-11-03 08:03:19', '03d159a3-0f88-424c-a24f-02f63855fe4f', '2020-11-06 02:59:17', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);
INSERT INTO `base_billrule` VALUES ('3762f47612ab4ea8a0af2e564ce8caf8', '流程单据-合同开票流程', 'WF_ConBillingNo', 'WF_CBL', 'yyyyMMdd', '4', '0001', 'WF_CBL201807200001', '1', 'WF_CBL202103230002', null, '0', '1', '2018-07-20 17:42:58', 'admin', '2021-03-23 08:00:23', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);
INSERT INTO `base_billrule` VALUES ('3f60e3f85f494b8592e477b87bf1af9a', '入库单号', 'rukudanhao', 'WF_RKDH', 'yyyyMMdd', '4', '0001', 'WF_RKDH202012040001', '2', 'WF_RKDH202012040002', '', '0', '1', '2020-12-04 01:03:28', '0a72e7d8-b519-435b-9c43-6796a31c72ec', '2021-01-22 02:16:33', 'admin', null, null, null);
INSERT INTO `base_billrule` VALUES ('409d87cffcdf490cab42bdd18f4a7e4b', '流程表单-薪酬发放', 'WF_PayDistributionNo', 'WF_PDB', 'yyyyMMdd', '4', '0001', 'WF_PDB201807170001', '1', 'WF_PDB202103230002', null, '0', '1', '2018-07-17 17:15:17', 'admin', '2021-03-23 07:14:13', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);
INSERT INTO `base_billrule` VALUES ('42d2a5a6ecca463fbf93422afc1f0416', '流程表单-销售订单', 'WF_SalesOrderNo', 'WF_SO', 'yyyyMMdd', '4', '0001', 'WF_SO201807130001', '1', 'WF_SO202106030001', null, '0', '1', '2018-07-10 21:06:29', 'admin', '2021-06-03 03:49:38', 'admin', null, null, null);
INSERT INTO `base_billrule` VALUES ('4840dcfa0c444bf58128634d80da8080', '流程表单-违章处理申请表', 'WF_ViolationHandlingNo', 'WF_VTH', 'yyyyMMdd', '4', '0001', 'WF_VTH201807170001', '1', 'WF_VTH202103230002', null, '0', '1', '2018-07-17 17:27:17', 'admin', '2021-03-23 07:12:24', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);
INSERT INTO `base_billrule` VALUES ('49a7fd329b894c5bbd95cec174723a8f', '流程表单-成品入库单', 'WF_FinishedProductNo', 'WF_FSD', 'yyyyMMdd', '4', '0001', 'WF_FSD201807160001', '1', 'WF_FSD202103230002', null, '0', '1', '2018-07-16 16:27:03', 'admin', '2021-03-23 07:24:45', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);
INSERT INTO `base_billrule` VALUES ('4e568feefd7c4aa48846ad997208a775', '流程表单-文件签阅表', 'WF_DocumentSigningNo', 'WF_DSG', 'yyyyMMdd', '4', '0001', 'WF_DSG201807130001', '1', 'WF_DSG202103230002', null, '0', '1', '2018-07-13 16:08:50', 'admin', '2021-03-23 08:04:44', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);
INSERT INTO `base_billrule` VALUES ('4ed1094654bb4a19be01e45c3644983d', '流程表单-合同申请单表', 'WF_ContractApprovalSheetNo', 'WF_CAS', 'yyyyMMdd', '4', '0001', 'WF_CAS201807130001', '1', 'WF_CAS202103230002', null, '0', '1', '2018-07-13 16:06:27', 'admin', '2021-03-23 08:00:58', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);
INSERT INTO `base_billrule` VALUES ('4eda5a4f6efa406bbda50703058418a6', '流程表单-车辆申请', 'WF_VehicleApplyNo', 'WF_VHA', 'yyyyMMdd', '4', '0001', 'WF_VHA201807170001', '1', 'WF_VHA202103230002', null, '0', '1', '2018-07-17 17:26:29', 'admin', '2021-03-23 07:50:56', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);
INSERT INTO `base_billrule` VALUES ('4f22aaa640d7450b837fc248464760c5', '流程表单-采购原材料', 'WF_ProcurementMaterialNo', 'WF_PMT', 'yyyyMMdd', '4', '0001', 'WF_PMT201807170001', '1', 'WF_PMT202106210001', null, '0', '1', '2018-07-17 17:18:04', 'admin', '2021-06-20 23:42:47', 'admin', null, null, null);
INSERT INTO `base_billrule` VALUES ('512cd7c5819a40da8008471f2a529414', '流程表单-领用办公用品申请表', 'WF_OfficeSuppliesNo', 'WF_OSL', 'yyyyMMdd', '4', '0001', 'WF_OSL201807180001', '1', 'WF_OSL202103230002', null, '0', '1', '2018-07-16 16:32:27', 'admin', '2021-03-23 07:59:33', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);
INSERT INTO `base_billrule` VALUES ('54afa2260d794c9c8576012836deb5f7', '流程表单-档案借阅申请', 'WF_ArchivalBorrowNo', 'WF_AVB', 'yyyyMMdd', '4', '0001', 'WF_AVB201807130001', '1', 'WF_AVB202103230002', null, '0', '1', '2018-07-13 16:02:20', 'admin', '2021-03-23 08:02:27', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);
INSERT INTO `base_billrule` VALUES ('636555655081427791f7f242dc2472e7', '流程表单-费用报销', 'feiyong', 'feiyong', 'yyyyMMdd', '3', '002', 'feiyong20201103002', '1', 'feiyong20210107001', '', '0', '1', '2020-11-03 08:12:04', '03d159a3-0f88-424c-a24f-02f63855fe4f', '2021-01-07 11:57:46', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);
INSERT INTO `base_billrule` VALUES ('65aa67aacb3a464782c91b7ceb132bad', '流程表单-借支单', 'WF_DebitBillNo', 'WF_DBL', 'yyyyMMdd', '4', '0001', 'WF_DBL201807130001', '1', 'WF_DBL202103230002', null, '0', '1', '2018-07-13 16:07:11', 'admin', '2021-03-23 07:22:21', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);
INSERT INTO `base_billrule` VALUES ('6b668981fd274b89b7b2046cf66aab1a', '采购单号', 'cgdh', 'cgdh', 'yyyyMMdd', '5', '00001', 'cgdh2020111200001', null, null, '', '0', '1', '2020-11-12 08:57:06', 'admin', null, null, null, null, null);
INSERT INTO `base_billrule` VALUES ('6f0f49e202c24b9e971323f1465c437e', '流程表单-差旅报销申请表', 'WF_TravelReimbursementNo', 'WF_TRB', 'yyyyMMdd', '4', '0001', 'WF_TRB201807130001', '1', 'WF_TRB202107230001', null, '0', '1', '2018-07-09 20:51:26', 'admin', '2021-07-23 12:55:50', 'admin', null, null, null);
INSERT INTO `base_billrule` VALUES ('7d71dd24b3074416802a567ab8d7cb8a', '流程表单-行文呈批表', 'WF_BatchTableNo', 'WF_BTB', 'yyyyMMdd', '4', '0001', 'WF_BTB201807130001', '1', 'WF_BTB202103230002', null, '0', '1', '2018-07-13 16:04:49', 'admin', '2021-03-23 08:05:41', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);
INSERT INTO `base_billrule` VALUES ('86eafe8dfc79453caf3427abf133a424', '流程表单-请假申请单号', 'WF_LeaveApplyNo', 'YGQJ-', 'yyyyMMdd', '4', '0001', 'YGQJ-201806110001', '2', 'YGQJ-202103230003', null, '0', '1', '2018-06-11 14:33:48', 'admin', '2021-03-23 09:28:38', 'admin', null, null, null);
INSERT INTO `base_billrule` VALUES ('86ef46b40d0948dfb02b2738827f35b6', '订单编号', 'dingdanbianhao', 'WF_DDBH', 'yyyyMMdd', '4', '0001', 'WF_DDBH202012020001', '1', 'WF_DDBH202012290001', '', '0', '1', '2020-12-02 07:36:28', 'admin', '2020-12-29 09:12:17', '048657c1-ecb1-4e5d-aced-6126ceae779e', null, null, null);
INSERT INTO `base_billrule` VALUES ('8738618bcbf04bddb4c66f6d677408fa', '流程表单-报表审批表', 'WF_QuotationApprovalNo', 'WF_QTA', 'yyyyMMdd', '4', '0001', 'WF_QTA201807200001', '1', 'WF_QTA202103230002', null, '0', '1', '2018-07-20 09:52:09', 'admin', '2021-03-23 07:36:20', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);
INSERT INTO `base_billrule` VALUES ('891dd4cf-8cec-4f32-8e03-95134f3d66d8', '订单流水号', 'OrderNumber', 'SALE-', 'yyyyMMdd', '4', '0001', 'SALE-201708150001', '1', 'SALE-202108260001', null, '0', '1', '2017-06-26 13:38:24', '7a727106-394f-4d70-87db-5e6d31bf2b88', '2021-08-26 15:04:15', 'admin', null, null, null);
INSERT INTO `base_billrule` VALUES ('8d5f443eb887493292b80f81525fffc0', '流程表单-入库申请单', 'WF_WarehouseReceiptNo', 'WF_WHR', 'yyyyMMdd', '4', '0001', 'WF_WHR201807170001', '1', 'WF_WHR202103230002', null, '0', '1', '2018-07-17 17:28:00', 'admin', '2021-03-23 07:24:37', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);
INSERT INTO `base_billrule` VALUES ('96f92e523621496680d16b2accc7f277', '报销单据', 'bxdj', 'bxdj', 'yyyyMMdd', '8', '00000001', 'bxdj2020111300000001', '1', 'bxdj2020122500000001', '', '0', '1', '2020-11-13 06:26:00', 'admin', '2020-12-25 02:13:50', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);
INSERT INTO `base_billrule` VALUES ('98eef2faaf794156ac17131fde72f551', '流程表单-工作联系单', 'WF_WorkContactSheetNo', 'WF_WCS', 'yyyyMMdd', '4', '0001', 'WF_WCS201807170001', '1', 'WF_WCS202103230002', null, '0', '1', '2018-07-17 17:28:40', 'admin', '2021-03-23 08:02:54', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);
INSERT INTO `base_billrule` VALUES ('a1d4b29e9c82417c8cc92464144e0677', '流程表单-行政赏罚单', 'WF_RewardPunishmentNo', 'WF_RPM', 'yyyyMMdd', '4', '0001', 'WF_RPM201807170001', '1', 'WF_RPM202103230002', null, '0', '1', '2018-07-17 17:23:00', 'admin', '2021-03-23 07:13:23', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);
INSERT INTO `base_billrule` VALUES ('aa0c0e66a275406990bd994781130036', '报销单据1', 'baoxiaodanju1', 'WF_BXDJ', 'yyyyMMdd', '4', '0001', 'WF_BXDJ202012030001', '1', 'WF_BXDJ202101180001', '', '0', '1', '2020-12-03 01:41:45', '0a72e7d8-b519-435b-9c43-6796a31c72ec', '2021-01-18 06:20:36', 'admin', null, null, null);
INSERT INTO `base_billrule` VALUES ('aa229059c92b4c3c9862d4107841fca1', '流程表单-批包装指令', 'WF_BatchPackNo', 'WF_BPK', 'yyyyMMdd', '4', '0001', 'WF_BPK201807130001', '1', 'WF_BPK202103230002', null, '0', '1', '2018-07-13 16:04:05', 'admin', '2021-03-23 07:24:51', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);
INSERT INTO `base_billrule` VALUES ('acb9ade6e5fc4022856e9f922dff8b66', '流程名称-出库单', 'WF_OutboundOrderNo', 'WF_OBD', 'yyyyMMdd', '4', '0001', 'WF_OBD201807160001', '1', 'WF_OBD202103230002', null, '0', '1', '2018-07-16 16:35:40', 'admin', '2021-03-23 07:24:40', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);
INSERT INTO `base_billrule` VALUES ('ace562927c664d589dbe5dcfaa062b2c', '流程表单-文件签批意见表', 'WF_DocumentApprovalNo', 'WF_DAV', 'yyyyMMdd', '4', '0001', 'WF_DAV201807130001', '1', 'WF_DAV202103230002', null, '0', '1', '2018-07-13 16:07:59', 'admin', '2021-03-23 08:05:14', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);
INSERT INTO `base_billrule` VALUES ('aced20ecf3e44843b033f529afc1767c', '流程表单-发货单申请', 'WF_ApplyDeliverGoodsNo', 'WF_ADG', 'yyyyMMdd', '4', '0001', 'WF_ADG201807130001', '1', 'WF_ADG202103230002', null, '0', '1', '2018-07-12 10:27:36', 'admin', '2021-03-23 07:24:57', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);
INSERT INTO `base_billrule` VALUES ('b6c46f32e513452ab576e63ebfa27119', '流程表单-用车申请', 'yongche', 'yongche', 'yyyyMMdd', '2', '02', 'yongche2020110302', '1', 'yongche2020110301', '', '0', '1', '2020-11-03 07:27:39', '03d159a3-0f88-424c-a24f-02f63855fe4f', '2020-11-03 09:14:16', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);
INSERT INTO `base_billrule` VALUES ('bdb81f6d38534a1293dc9165c026064c', '流程表单-员工加班申请单', 'WF_StaffOvertimeNo', 'WF_SOT', 'yyyyMMdd', '4', '0001', 'WF_SOT201807170001', '1', 'WF_SOT202103230002', null, '0', '1', '2018-07-17 17:24:08', 'admin', '2021-03-23 07:33:58', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);
INSERT INTO `base_billrule` VALUES ('c1811bda3bb34b0a95f86e186233349c', '流程表单-月工作总结', 'WF_MonthlyReportNo', 'WF_MRP', 'yyyyMMdd', '4', '0001', 'WF_MRP201807200001', '1', 'WF_MRP202103230002', null, '0', '1', '2018-07-20 14:58:35', 'admin', '2021-03-23 07:40:53', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);
INSERT INTO `base_billrule` VALUES ('ccd506c5a30a487694d8ffbee895930e', '流程表单-用品入库申请表', 'WF_ArticlesWarehousNo', 'WF_AWH', 'yyyyMMdd', '4', '0001', 'WF_AWH201807130001', '1', 'WF_AWH202103230002', null, '0', '1', '2018-07-13 16:03:05', 'admin', '2021-03-23 07:24:54', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);
INSERT INTO `base_billrule` VALUES ('cdf76d1c376d4cd3a4ba21adda8fa017', '还车申请单据号', 'huancheshenqingdanjuhao', 'WF_HCSQ', 'yyyyMMdd', '4', '0001', 'WF_HCSQ202012030001', '4', 'WF_HCSQ202012030004', '', '0', '1', '2020-12-03 01:21:29', 'admin', '2020-12-03 01:26:12', '0a72e7d8-b519-435b-9c43-6796a31c72ec', null, null, null);
INSERT INTO `base_billrule` VALUES ('ced4570d3cd040ecb58f7e826b98f3bc', '流程表单-领料单', 'WF_MaterialRequisitionNo', 'WF_MRT', 'yyyyMMdd', '4', '0001', 'WF_MRT201807160001', '1', 'WF_MRT202103230002', null, '0', '1', '2018-07-16 21:25:20', 'admin', '2021-03-23 07:24:43', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);
INSERT INTO `base_billrule` VALUES ('cfd76f276ed14cc8a7af49e41faa6d9b', '用车申请单号', 'yongcheshenqingdanhao', 'WF_YCSQDH', 'yyyyMMdd', '4', '0001', 'WF_YCSQDH202012030001', '3', 'WF_YCSQDH202012290003', '', '0', '0', '2020-12-03 02:55:04', '0a72e7d8-b519-435b-9c43-6796a31c72ec', '2020-12-29 09:13:19', '048657c1-ecb1-4e5d-aced-6126ceae779e', null, null, null);
INSERT INTO `base_billrule` VALUES ('dc3ed8107e484e56aa420ca220614f38', '流程表单-收文签呈单', 'WF_ReceiptSignNo', 'WF_RSG', 'yyyyMMdd', '4', '0001', 'WF_RSG201807170001', '1', 'WF_RSG202103230002', null, '0', '1', '2018-07-17 17:22:12', 'admin', '2021-03-23 08:03:17', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);
INSERT INTO `base_billrule` VALUES ('de43f95123024df89448213cd2459a8d', '楼宇号', 'lhy', 'lyh', 'yyyyMMdd', '5', '00001', 'lyh2020111200001', '1', 'lyh2021012200001', '', '0', '1', '2020-11-12 07:09:13', 'admin', '2021-01-22 01:34:05', 'admin', null, null, null);
INSERT INTO `base_billrule` VALUES ('de67e9251844440092dbefa43b7b555d', '订单编号1', 'dingdanbianhao1', 'WF_DDBH', 'yyyyMMdd', '4', '0001', 'WF_DDBH202012030001', '2', 'WF_DDBH202103230003', '', '0', '1', '2020-12-03 08:40:32', '0a72e7d8-b519-435b-9c43-6796a31c72ec', '2021-03-23 07:09:26', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);
INSERT INTO `base_billrule` VALUES ('e2a9138a80fe4c7e92375169eb0aa59e', '动态表单', 'DynamicNumber1', 'WF_Dy', 'yyyyMMdd', '4', '0001', 'WF_Dy202008130001', '1', 'WF_Dy202010120001', '动态表单单据', '0', '0', '2020-08-13 11:38:58', null, '2020-11-04 10:08:12', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);
INSERT INTO `base_billrule` VALUES ('e6329fc59b5346b8928bcac44aa19a95', '流程表单-补卡申请', 'WF_SupplementCardNo', 'WF_SMC', 'yyyyMMdd', '4', '0001', 'WF_SMC201807170001', '1', 'WF_SMC202103230002', null, '0', '1', '2018-07-17 17:24:55', 'admin', '2021-03-23 07:32:41', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);
INSERT INTO `base_billrule` VALUES ('e7c8ac0d78fe4c31a8c0aa3323646ec9', '流程表单-出差预支申请单', 'WF_TravelApplyNo', 'WF_TRA', 'yyyyMMdd', '4', '0001', 'WF_TRA201807170001', '1', 'WF_TRA202107090001', null, '0', '1', '2018-07-17 17:25:41', 'admin', '2021-07-09 12:38:18', 'admin', null, null, null);
INSERT INTO `base_billrule` VALUES ('e8de655fffc9459eb8dce4f226b75c91', '报销单号', 'baoxiaodanhao', 'WF_BXDH', 'yyyyMMdd', '4', '0001', 'WF_BXDH202012030001', '3', 'WF_BXDH202103230004', '', '0', '1', '2020-12-03 05:47:53', '0a72e7d8-b519-435b-9c43-6796a31c72ec', '2021-03-23 08:12:39', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);
INSERT INTO `base_billrule` VALUES ('f234751bfdc342dcaf297ef83add34c6', '用车申请单', 'yongcheshenqing', 'yongche', 'yyyyMMdd', '5', '00001', 'yongche2020110600001', '3', 'yongche2021032300004', '', '0', '1', '2020-11-06 06:19:02', 'admin', '2021-03-23 09:31:20', 'admin', null, null, null);
INSERT INTO `base_billrule` VALUES ('f70ce93e4590466fb176ef69bc371bd1', '流程表单-合同审批', 'WF_ContractApprovalNo', 'WF_CAV', 'yyyyMMdd', '4', '0001', 'WF_CAV201807130001', '1', 'WF_CAV202103230002', null, '0', '1', '2018-07-13 16:05:45', 'admin', '2021-03-23 08:01:30', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);
INSERT INTO `base_billrule` VALUES ('fa86e300d6da4d6c84b707449640ad84', '流程表单-收入确认分析表', 'WF_IncomeRecognitionNo', 'WF_IRG', 'yyyyMMdd', '4', '0001', 'WF_IRG201807160001', '1', 'WF_IRG202103230002', null, '0', '1', '2018-07-16 16:27:54', 'admin', '2021-03-23 07:17:21', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);
INSERT INTO `base_billrule` VALUES ('ff92de3d3484476cac185ff242d298ec', '流程表单-外出申请单', 'WF_OutgoingApplyNo', 'WF_OGA', 'yyyyMMdd', '4', '0001', 'WF_OGA201807170001', '1', 'WF_OGA202103230002', null, '0', '1', '2018-07-16 16:37:31', 'admin', '2021-03-23 07:34:26', '03d159a3-0f88-424c-a24f-02f63855fe4f', null, null, null);

-- ----------------------------
-- Table structure for `base_dictionarydata`
-- ----------------------------
DROP TABLE IF EXISTS `base_dictionarydata`;
CREATE TABLE `base_dictionarydata` (
  `Id` varchar(50) NOT NULL COMMENT '自然主键',
  `ParentId` varchar(50) DEFAULT NULL COMMENT '上级',
  `FullName` varchar(50) DEFAULT NULL COMMENT '名称',
  `EnCode` varchar(50) DEFAULT NULL COMMENT '编号',
  `SimpleSpelling` longtext COMMENT '拼音',
  `IsDefault` int(11) DEFAULT NULL COMMENT '默认',
  `Description` longtext COMMENT '描述',
  `SortCode` bigint(20) DEFAULT NULL COMMENT '排序',
  `EnabledMark` int(11) DEFAULT NULL COMMENT '有效标志',
  `CreatorTime` datetime DEFAULT NULL COMMENT '创建时间',
  `CreatorUserId` varchar(50) DEFAULT NULL COMMENT '创建用户',
  `LastModifyTime` datetime DEFAULT NULL COMMENT '修改时间',
  `LastModifyUserId` varchar(50) DEFAULT NULL COMMENT '修改用户',
  `DeleteMark` int(11) DEFAULT NULL COMMENT '删除标志',
  `DeleteTime` datetime DEFAULT NULL COMMENT '删除时间',
  `DeleteUserId` varchar(50) DEFAULT NULL COMMENT '删除用户',
  `DictionaryTypeId` varchar(50) DEFAULT NULL COMMENT '类别主键',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='字典数据';

-- ----------------------------
-- Records of base_dictionarydata
-- ----------------------------
INSERT INTO `base_dictionarydata` VALUES ('00027e3ecaf54f008a5d7e25c6d4f4c3', '963255a34ea64a2584c5d1ba269c1fe6', '保密', '3', '3', null, '', '0', '1', '2020-08-15 03:11:52', '00ca450b-141b-45d2-b6cb-94d818165543', '2020-08-15 03:17:02', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, '963255a34ea64a2584c5d1ba269c1fe6');
INSERT INTO `base_dictionarydata` VALUES ('00C3CCB3-47E1-4FA3-8F8E-3AF6EA6D808A', 'd9b969ed-9122-4861-9035-c631acf99378', '文艺演出', '文艺演出', 'WYYC', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('012710bb94ae4dcb9fe21265c25bb17f', '0', '转账', 'transferPay', 'ZZ', null, '', '5', '1', '2021-09-02 11:16:51', 'admin', '2021-09-02 11:17:11', 'admin', null, null, null, 'e14b3a85a37048c8aa39ca97570fb18c');
INSERT INTO `base_dictionarydata` VALUES ('01ba59c5d9a44d71ab0284cb48525813', '0', '达斡尔族', '达斡尔族', 'DWEZ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('029801dc74fd4fd1bc432ca9aba759dd', '65e7917344fa460e8c487e45bbbab26f', '费用相关', 'costRelated', 'FYXG', null, '', '0', '1', '2020-09-21 02:20:43', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '65e7917344fa460e8c487e45bbbab26f');
INSERT INTO `base_dictionarydata` VALUES ('02C0A09B-1FD7-4B1A-BCD1-9F368AEE9066', '4d885793-2fbd-49ce-9b0e-aa709a71b2d0', '景区/公园/游乐', '景区/公园/游乐', 'JQGYYY,JQGYYL,JOGYYY,JOGYYL', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('02c8e5984d484358beff9cdff599992b', '1cff2b60fd7a4c9f82273163b956268c', '人事管理', 'personnelManage', 'RSGL', null, '', '0', '1', '2020-09-21 02:00:38', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '1cff2b60fd7a4c9f82273163b956268c');
INSERT INTO `base_dictionarydata` VALUES ('03fdd2708ade428a97edb2b21e3c4458', '4173579c31e74a2b8749e65a23e5c957', '费用相关', 'costRelated', 'FYXG', null, '', '0', '1', '2020-09-21 01:58:56', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '4173579c31e74a2b8749e65a23e5c957');
INSERT INTO `base_dictionarydata` VALUES ('059275f8dc94475d82efd6780d2f67b6', '0', '锡伯族', '锡伯族', 'XBZ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('06cf989b086a432c9e507e88117e37f5', '0', '网银支付', 'Onlinepayment', 'WYZF', null, null, '0', '1', '2018-07-10 10:26:05', 'admin', '2018-07-10 10:56:02', 'admin', null, null, null, '1143c18426014419a35aa1fa97685c9c');
INSERT INTO `base_dictionarydata` VALUES ('06daee5aa4ce4c4595c654e37b0c1b0c', '038d293fca544c86afa3f2bc65421fd7', '新增意向客户', '2', 'XZYXKH', null, '', '0', '1', '2020-11-06 06:43:30', 'admin', '2021-01-21 08:48:46', 'admin', null, null, null, '038d293fca544c86afa3f2bc65421fd7');
INSERT INTO `base_dictionarydata` VALUES ('06DBA3B3-B7E5-4631-9718-8B3160EE289C', '27c38cf2-dc0a-4449-ab87-7eb68f7e425b', '兴趣辅导/特长教育', '兴趣辅导/特长教育', 'XQFDTZJY,XQFDTCJY', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('074a2706adc84abe8832b0b6a9f20229', '0', '国有企业', '1', 'GYQY', '0', null, '0', '1', '2017-10-25 14:34:31', null, '2017-10-26 17:14:06', null, null, null, null, '9b542177a477488994ce09fff3c93901');
INSERT INTO `base_dictionarydata` VALUES ('077600c9ac6b4e769c20e5c638bd2ff9', '1e31cad8cf5448a9859d8551015eb6f0', '女', '2', 'N', null, '', '0', '1', '2020-11-04 06:48:50', 'admin', null, null, null, null, null, '1e31cad8cf5448a9859d8551015eb6f0');
INSERT INTO `base_dictionarydata` VALUES ('07b315252c644637ae121befcfaef98e', '037ba904515348eaad1c4bd462fc80a6', '合同管理', 'contractManage', 'HTGL', null, '', '0', '1', '2020-09-21 02:03:08', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '037ba904515348eaad1c4bd462fc80a6');
INSERT INTO `base_dictionarydata` VALUES ('07b7ab0a7e624363bdc0a82e49f025ed', '0', '费用相关', '2', 'FYXG', null, null, '0', '1', '2020-05-16 17:02:46', 'admin', '2020-05-16 17:05:33', 'admin', null, null, null, 'e0bfb7f7a3054e65949ca0f7c136d717');
INSERT INTO `base_dictionarydata` VALUES ('07F0018A-897A-4D27-BBF5-9567D8F78A7B', '8e63a940-4e88-461f-b80d-fd3db065bfef', '水/电/暖/气', '水/电/暖/气', 'SDNQ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('07f10a73a43942969e7b75a6d14c4e17', '0', '俄罗斯族', '俄罗斯族', 'ELSZ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('091f83fedfa3424d93f47c6845c342dc', '0', '繁体中文', 'zh-TW', 'PTZW,FTZW', null, null, '0', '1', '2017-10-24 16:00:36', null, '2018-09-06 11:27:24', 'admin', null, null, null, 'dc6b2542d94b407cac61ec1d59592901');
INSERT INTO `base_dictionarydata` VALUES ('093461964de74d75a5a0cb3a0f3bd46c', '4173579c31e74a2b8749e65a23e5c957', '仓库管理', 'warehouseManagement', 'CKGL', null, '', '0', '1', '2020-09-21 01:59:51', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '4173579c31e74a2b8749e65a23e5c957');
INSERT INTO `base_dictionarydata` VALUES ('097a648ca21943c8ab775d169b893725', '0', '简体中文', 'zh-CN', 'JTZW', '1', null, '0', '1', '2017-10-24 16:00:36', null, '2018-09-06 11:27:12', 'admin', null, null, null, 'dc6b2542d94b407cac61ec1d59592901');
INSERT INTO `base_dictionarydata` VALUES ('0A5B92DD-3236-45E7-BD1B-CC92EE212135', '75f1eb60-1258-4747-bfbc-ac66c1724b49', '商务中介/外贸报关代理', '商务中介/外贸报关代理', 'SWZJWMBGDL', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('0A5BECDA-4840-4DAA-94A6-3564B744B35E', 'b5dc651f-1125-4c6c-8114-24e2ef85ed73', '养生/按摩', '养生/按摩', 'YSAM', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('0b5d86db35764ae593918b0d5c0dacc8', '3e2d33e54d554b4b8a33fc41b93f8a2d', '薪酬费用', '5', 'XCFY', null, '', '0', '1', '2020-10-31 01:57:41', 'admin', null, null, null, null, null, '3e2d33e54d554b4b8a33fc41b93f8a2d');
INSERT INTO `base_dictionarydata` VALUES ('0bb8dce3cd934b5da4c31be379fdb962', '0', '景颇族', '景颇族', 'JPZ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('0c31b14f1d1743268ae54a1694a7ea14', '0', '线上询价', '线上询价', 'XSXJ', null, null, '0', '1', '2019-12-16 17:52:42', 'admin', null, null, null, null, null, 'c9f32bb4eef748998ed57280fe100fb7');
INSERT INTO `base_dictionarydata` VALUES ('0cc616a324d94e4d868d75e93c2ef044', '0', '合同管理', '4', 'HTGL,GTGL', null, null, '0', '1', '2020-05-16 17:03:53', 'admin', '2020-05-16 17:05:42', 'admin', null, null, null, 'e0bfb7f7a3054e65949ca0f7c136d717');
INSERT INTO `base_dictionarydata` VALUES ('0d181414d82442a296473cac4fa357ec', '0', '@2980', '@2980.com', '', null, '网络邮箱', '0', '1', '2018-11-30 11:28:24', 'admin', null, null, null, null, null, '55d7666d74df4bb4b36aed08a3a5bad1');
INSERT INTO `base_dictionarydata` VALUES ('0DB2D286-D299-4490-808B-A94A217E45DD', 'c6edee1e-ae57-4b1e-84e5-4837e24978b8', '政府', '政府', 'ZF', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('0F2C0CD7-D2ED-4119-8E06-80D928B6F6DD', 'd9b969ed-9122-4861-9035-c631acf99378', '洗浴', '洗浴', 'XY', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('0F3966B0-63A5-47B1-A88B-D8CDFE65563E', '75f1eb60-1258-4747-bfbc-ac66c1724b49', '货物运输/大宗物流', '货物运输/大宗物流', 'HWYSDZWL', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('0fcc751d-9ceb-4767-8755-f0189b239468', 'd59a3cf65f9847dbb08be449e3feae16', '房产/装修/租售', '房产/装修/租售', 'FCZXZS', null, null, '0', '1', '2017-10-24 16:00:36', null, '2021-01-22 02:02:52', 'admin', null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('1128f7a50cc64c6db6eed4696fdbee80', '0', '研究生', '7', 'YJS', null, null, '0', '1', '2017-10-24 16:00:35', null, '2020-09-28 01:10:01', 'admin', null, null, null, '6a6d6fb541b742fbae7e8888528baa16');
INSERT INTO `base_dictionarydata` VALUES ('113779bb677d4021b54b10eb8c29145a', '0', '壮族', '壮族', 'ZZ', null, null, '0', '1', '2017-10-24 16:00:35', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('125a9e95ea0f4c49a195c47a5a79deee', '0', '空置', 'empty', 'KZ', null, '', '0', '1', '2021-08-17 02:20:00', 'admin', null, null, null, null, null, 'f205aaa2883a42dc820f253c76db054a');
INSERT INTO `base_dictionarydata` VALUES ('128ACB5A-3619-4D41-86A6-9CC8CE44F0C9', '27c38cf2-dc0a-4449-ab87-7eb68f7e425b', '留学/移民', '留学/移民', 'LXYM', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('12f83c695e104710933f9d713ac68b32', '0', '京族', '京族', 'JZ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('134d1bdae57e4d6c898e0d2ee72f2164', '0', '黎族', '黎族', 'LZ', null, null, '0', '1', '2017-10-24 16:00:35', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('13e7e17124b0404bb6411c4d11432cdb', '0', '微信支付', 'wxPay', 'WXZF', null, '', '1', '1', '2021-09-02 11:12:58', 'admin', null, null, null, null, null, 'e14b3a85a37048c8aa39ca97570fb18c');
INSERT INTO `base_dictionarydata` VALUES ('176a35a32f6847db9d6699b35e96b662', '3e2d33e54d554b4b8a33fc41b93f8a2d', '人事费用', '1', 'RSFY', null, '', '1', '1', '2020-10-31 01:56:25', 'admin', '2021-01-20 11:59:14', 'admin', null, null, null, '3e2d33e54d554b4b8a33fc41b93f8a2d');
INSERT INTO `base_dictionarydata` VALUES ('18d12755e3674a4298ea45e3fae785dd', '0', '物业管理', 'wuye', 'WYGL', null, '', '0', '1', '2021-08-17 02:39:40', 'admin', null, null, null, null, null, '765929a127f44a5b80e773d65d58f96c');
INSERT INTO `base_dictionarydata` VALUES ('19e53c01f2aa4cfc880eb9ced1055755', '0', '已售出', 'selled', 'YSC', null, '', '0', '1', '2021-08-17 02:21:31', 'admin', null, null, null, null, null, 'f205aaa2883a42dc820f253c76db054a');
INSERT INTO `base_dictionarydata` VALUES ('1ae0d5056618489f941edc485c2ae772', '0', '小于', '4', 'XY', null, null, '0', '1', '2017-10-24 16:00:36', null, '2017-10-26 17:17:37', null, null, null, null, '237446e245ce403d8062995ea33711cf');
INSERT INTO `base_dictionarydata` VALUES ('1aea9914615748c1a150ebc52a102e60', '0', '搜索引擎', '搜索引擎', 'SSYQ', null, null, '0', '1', '2019-12-16 17:52:09', 'admin', null, null, null, null, null, 'c9f32bb4eef748998ed57280fe100fb7');
INSERT INTO `base_dictionarydata` VALUES ('1b5fc8800abc468ca6af903149caa5a4', '0', '半年结算', 'Halfyearknot', 'BNJS', null, null, '0', '1', '2018-07-10 10:24:58', 'admin', '2018-07-10 10:54:11', 'admin', null, null, null, '1143c18426014419a35aa1fa97685c9c');
INSERT INTO `base_dictionarydata` VALUES ('1b6da0db9c8f4054ac5225d32ac2607a', '0', '普米族', '普米族', 'PMZ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('1b82cfae-98f1-4b54-a3ae-1d1c6ad3f3f6', '0', '手机数码/通讯服务', '手机数码/通讯服务', 'SJSMTXFW', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('1c8bc07c65214f7ca842d8504cea4067', '0', '东乡族', '东乡族', 'DXZ', null, null, '0', '1', '2017-10-24 16:00:35', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('1cc02221b9a6499f89bc116000b91069', '0', '动态费用', 'dynamic', 'DTFY', null, '', '2', '1', '2021-08-20 15:10:03', 'admin', '2021-08-20 15:10:17', 'admin', null, null, null, '7a10e1a5fbc348d8892399ed9b1ffe37');
INSERT INTO `base_dictionarydata` VALUES ('1CCFE5C7-9448-44C1-A7A0-206DA6095868', '7c7d4ae2-455c-439b-b8a8-eb484014aa75', '维修/保养/改装', '维修/保养/改装', 'WXBYGZ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('1d53970d6da94c6ca648311399122a02', '0', '哈萨克族', '哈萨克族', 'HSKZ', null, null, '0', '1', '2017-10-24 16:00:35', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('1db9a7c7c88a4dc7a5f61ae3aed723ae', '0', '撒拉族', '撒拉族', 'SLZ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('1E8757B0-9572-43B7-B5B0-CE15FDC9AAF2', '614ecab4-845f-4cbc-9ff7-a9968aca6eeb', '安防/监控器材', '安防/监控器材', 'AFJKQC', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('20720625593343e39676654e73a6a39a', '0', '朝鲜族', '朝鲜族', 'ZXZ,CXZ', null, null, '0', '1', '2017-10-24 16:00:35', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('2112FC39-0B0C-4D4D-87CB-A48843674083', '0fcc751d-9ceb-4767-8755-f0189b239468', '开发商', '开发商', 'KFS', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('22DF536C-B5FF-44BA-A53E-66DD314055E0', '4d885793-2fbd-49ce-9b0e-aa709a71b2d0', '客运/票务', '客运/票务', 'KYPW', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('2498cd869da94d60b7c37c9afb8cc557', '70f970e14fee4879802b63122511032b', '自定义表单', '2', 'DTBD', null, '', '0', '1', '2020-08-10 18:11:36', '00ca450b-141b-45d2-b6cb-94d818165543', '2020-12-03 09:30:35', 'admin', null, null, null, '70f970e14fee4879802b63122511032b');
INSERT INTO `base_dictionarydata` VALUES ('249c53f9a6fc4be2b7ed1c9505daa863', 'b1474890faaa47e1858ce7b593c8ffd7', '人事管理', 'personnelManage', 'RSGL', null, '', '0', '1', '2020-09-21 02:22:14', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, 'b1474890faaa47e1858ce7b593c8ffd7');
INSERT INTO `base_dictionarydata` VALUES ('25b952c507b148f98d50762a2e90419a', '0', '裕固族', '裕固族', 'YGZ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('25c403a66a7a43cab1f85f105e5ba394', '038d293fca544c86afa3f2bc65421fd7', '商机', '1', 'SJ', null, '', '0', '1', '2020-11-06 06:43:20', 'admin', null, null, null, null, null, '038d293fca544c86afa3f2bc65421fd7');
INSERT INTO `base_dictionarydata` VALUES ('2602c057c8ee4cc78857465379d6c822', '1cff2b60fd7a4c9f82273163b956268c', '合同管理', 'contractManage', 'HTGL', null, '', '0', '1', '2020-09-21 02:01:07', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '1cff2b60fd7a4c9f82273163b956268c');
INSERT INTO `base_dictionarydata` VALUES ('26f5cd7cd65b49548043bcdd72d3afdf', '4173579c31e74a2b8749e65a23e5c957', '合同管理', 'contractManage', 'HTGL', null, '', '0', '1', '2020-09-21 01:59:12', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '4173579c31e74a2b8749e65a23e5c957');
INSERT INTO `base_dictionarydata` VALUES ('2716A909-1CAC-4A72-8749-28206D3326E0', 'd9b969ed-9122-4861-9035-c631acf99378', '电影院', '电影院', 'DYY', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('2745E89F-AA85-49EB-943C-9037D7B73535', '0fcc751d-9ceb-4767-8755-f0189b239468', '家具/家居', '家具/家居', 'JJJJ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('27c38cf2-dc0a-4449-ab87-7eb68f7e425b', '0', '教育/出国', '教育/出国', 'JYCG', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('27d8cf5a-22e8-4e05-b4d6-5684a564418a', '0', '计算机/互联网', '计算机/互联网', 'JSJHLW', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('27efd59ba0ff484e88a528b8dc678cdf', '573c31998bc04a23a769f9a9eff67d00', '仓库管理', 'warehouseManagement', 'CKGL', null, '', '0', '1', '2020-09-21 01:58:26', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '573c31998bc04a23a769f9a9eff67d00');
INSERT INTO `base_dictionarydata` VALUES ('2831f140de934c0fafc1188e2c387a8d', '0', '行政管理', '8', 'XZGL,HZGL', null, null, '0', '1', '2020-05-16 17:04:43', 'admin', '2020-05-16 17:06:10', 'admin', null, null, null, 'e0bfb7f7a3054e65949ca0f7c136d717');
INSERT INTO `base_dictionarydata` VALUES ('2835dcf61cf04a0e97bc4b117b08e3cb', '0', '哈尼族', '哈尼族', 'HNZ', null, null, '0', '1', '2017-10-24 16:00:35', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('2876ae29ce5d4a41baa007f2977cfc85', '0', '默认角色', 'DefaultRole', 'MRJS', null, null, '0', '1', '2017-10-27 10:24:01', null, '2018-01-20 14:55:11', 'admin', null, null, null, '4501f6f26a384757bce12d4c4b03342c');
INSERT INTO `base_dictionarydata` VALUES ('287ac847ef2a4f4b9198300477e8c4d9', '0', '毛南族', '毛南族', 'MNZ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('28C182AE-BCD2-48DA-97FC-C45383A58C68', '4d885793-2fbd-49ce-9b0e-aa709a71b2d0', '农家乐/度假村', '农家乐/度假村', 'NJYDJC,NJLDJC', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('2a3fa24dcace4a7aafeddb3d8c0ec65a', '0', '个体户', '5', 'GTH', null, null, '0', '1', '2017-10-25 14:36:21', null, '2017-10-26 17:14:22', null, null, null, null, '9b542177a477488994ce09fff3c93901');
INSERT INTO `base_dictionarydata` VALUES ('2aa6c64c8875401fa2ab862e3ac6f0e2', '0', '鄂温克族', '鄂温克族', 'EWKZ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('2af8606d2e184e29a287b2774aa6d46a', '0', '季度结算', 'Quarterlyknot', 'JDJS', null, null, '0', '1', '2018-07-10 10:24:19', 'admin', '2018-07-10 10:54:05', 'admin', null, null, null, '1143c18426014419a35aa1fa97685c9c');
INSERT INTO `base_dictionarydata` VALUES ('2b4dc588eb8f4c83b4dc7d70deb317dc', '0', '报表', '5', 'BB', null, '', '4', '1', '2020-08-05 15:23:19', '00ca450b-141b-45d2-b6cb-94d818165543', '2021-01-22 11:49:41', 'admin', null, null, null, 'e3ca5fe13c484a74bf0a4ea69eec1afd');
INSERT INTO `base_dictionarydata` VALUES ('2cd25306b6ed4fd4ad641c500d7205f9', '02ad722fd1914c338d51597236ad2339', '项目管理', '2', 'XMGL', null, '', '2', '1', '2020-11-06 06:01:00', 'admin', '2020-11-06 06:01:35', 'admin', null, null, null, '02ad722fd1914c338d51597236ad2339');
INSERT INTO `base_dictionarydata` VALUES ('2d55ff7e76334ab383bc05a859578d88', '42b0f46122b041d097f1fe4f2f637a03', '功能流程', '2', 'GNLC', null, '', '0', '1', '2020-08-10 18:59:22', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '42b0f46122b041d097f1fe4f2f637a03');
INSERT INTO `base_dictionarydata` VALUES ('2f978088acb4432593bf5a37257b19a2', 'c7e90b2ceaa647159490f7637f907290', '文化传媒', '文化传媒', 'WHZM,WHCM', null, null, '0', '1', '2019-12-16 17:54:55', 'admin', '2020-09-19 10:19:56', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, 'c7e90b2ceaa647159490f7637f907290');
INSERT INTO `base_dictionarydata` VALUES ('2FD33FE4-D636-4BC0-8A66-984D71387428', '27c38cf2-dc0a-4449-ab87-7eb68f7e425b', '职业培训/企业管理', '职业培训/企业管理', 'ZYPXQYGL', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('2FE19FDC-6308-441D-A10A-C1B2E33FBE87', '614ecab4-845f-4cbc-9ff7-a9968aca6eeb', '电子/IC/集成电路', '电子/IC/集成电路', 'DZJCDL', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('3002b702b8b44e979087821c7b3f2c20', 'b1474890faaa47e1858ce7b593c8ffd7', '销售管理', 'saleManage', 'XSGL', null, '', '0', '1', '2020-09-21 02:23:04', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, 'b1474890faaa47e1858ce7b593c8ffd7');
INSERT INTO `base_dictionarydata` VALUES ('30177502e07d479ca6825121716639d9', '0', '@wo', '@wo.cn', '', null, '沃邮箱', '0', '1', '2018-11-30 11:32:20', 'admin', null, null, null, null, null, '55d7666d74df4bb4b36aed08a3a5bad1');
INSERT INTO `base_dictionarydata` VALUES ('30be5ef4e3074dd89385ad6b4540c63d', '9c43287481364d348c0ea0d0f64b38be', '默认', '1', 'MR', null, '', '0', '1', '2020-08-04 14:45:15', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '9c43287481364d348c0ea0d0f64b38be');
INSERT INTO `base_dictionarydata` VALUES ('31a6e874c82045c9aa598508d58ea586', '0', '工勤岗位', '3', 'GQGW', null, '工勤岗位指承担技能操作和维护、后勤保障、服务等职责的工作岗位', '0', '1', '2017-10-26 13:29:25', null, '2017-10-26 17:14:36', null, null, null, null, 'dae93f2fd7cd4df999d32f8750fa6a1e');
INSERT INTO `base_dictionarydata` VALUES ('3206B717-97FC-4249-8AAA-4743992C7538', 'c6edee1e-ae57-4b1e-84e5-4837e24978b8', '行业组织', '行业组织', 'XYZZ,HYZZ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('33311B5D-5566-4E8B-A367-5BB23D32553B', '27d8cf5a-22e8-4e05-b4d6-5684a564418a', '计算机硬件', '计算机硬件', 'JSJYJ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('33859881-6714-470C-9CAC-F2B4C34FB0A9', '44338471-6e77-4dd0-899d-ea0355c03277', '中式美食', '中式美食', 'ZSMS', null, '', '0', '1', '2017-10-24 16:00:36', null, '2020-05-29 08:56:38', 'admin', null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('33A0777B-51C0-4A71-B99F-2CBB081FF93F', '44338471-6e77-4dd0-899d-ea0355c03277', '小吃快餐', '小吃快餐', 'XCKC', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('33cf9894fe3f415086796946acde9a21', '0', '怒族', '怒族', 'NZ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('343165982f9d4cef80cc11f98c9b3b03', '0', '@188', '@188.com', '', null, '财富邮', '0', '1', '2018-11-30 11:34:53', 'admin', null, null, null, null, null, '55d7666d74df4bb4b36aed08a3a5bad1');
INSERT INTO `base_dictionarydata` VALUES ('3547c4e289194e92854fec4cd84836b2', '0', '小学', '1', 'XX', '0', null, '0', '1', '2017-10-24 16:00:35', null, '2020-08-05 14:21:57', '1a2b0254-8862-4525-ab4f-444df590b2b8', null, null, null, '6a6d6fb541b742fbae7e8888528baa16');
INSERT INTO `base_dictionarydata` VALUES ('355f047f3e7540b8b55b168eec41ea5a', '0', '@139', '@139.com', '', null, '中国移动', '0', '1', '2018-11-30 11:19:56', 'admin', '2018-11-30 11:33:48', 'admin', null, null, null, '55d7666d74df4bb4b36aed08a3a5bad1');
INSERT INTO `base_dictionarydata` VALUES ('3676319f3e0b444e9f754a1691de696c', '0', '侗族', '侗族', 'TZ,DZ', null, null, '0', '1', '2017-10-24 16:00:35', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('367dab9c9b6640c6b24b1ec94bfbdeea', '0', '大屏', '6', 'DP', null, '', '5', '1', '2020-08-05 15:23:28', '00ca450b-141b-45d2-b6cb-94d818165543', '2021-01-22 11:49:48', 'admin', null, null, null, 'e3ca5fe13c484a74bf0a4ea69eec1afd');
INSERT INTO `base_dictionarydata` VALUES ('3722d977cbb54806abe2fed651547d01', '0', '月结', 'Monthlyknot', 'YJ', null, null, '0', '1', '2018-07-10 10:23:49', 'admin', null, null, null, null, null, '1143c18426014419a35aa1fa97685c9c');
INSERT INTO `base_dictionarydata` VALUES ('37bf7db5a5c94dc6a3485ccccb1c6447', '0', '瑶族', '瑶族', 'YZ', null, null, '0', '1', '2017-10-24 16:00:35', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('37e75a93dfc9417290f007eac6c732cd', '0', '提前一年', 'year', 'TQYN', null, '', '0', '1', '2021-08-20 17:49:33', 'admin', null, null, null, null, null, '30a825bd5467498cbf83b96b1cf29529');
INSERT INTO `base_dictionarydata` VALUES ('38B8C27D-7AEF-4B30-95A6-7B3472E12F99', '7d7dd051-6e57-499b-ac88-c019d7c663da', '医疗/护理/卫生', '医疗/护理/卫生', 'YLHLWS', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('38ca86c845244584ba8c77565d695eb7', '4173579c31e74a2b8749e65a23e5c957', '行政管理', 'administrationManage', 'XZGL', null, '', '0', '1', '2020-09-21 01:59:39', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '4173579c31e74a2b8749e65a23e5c957');
INSERT INTO `base_dictionarydata` VALUES ('39ab7bcf8d7d4f7a8b3ad34aae471ae9', '0', '商铺费用', 'house', 'SPFY', null, '', '0', '1', '2021-08-20 18:13:04', 'admin', null, null, null, null, null, '364e4a28d15640559122865180f98cfc');
INSERT INTO `base_dictionarydata` VALUES ('39AEC832-D76F-4635-AF3C-1F249C22010B', '8e63a940-4e88-461f-b80d-fd3db065bfef', '宠物', '宠物', 'CW', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('3b6c150a092e44bda357cfb1a0d51461', '0', '水族', '水族', 'SZ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('3d1f4cf346524fdcbdee0b523b006eae', '0', '@yeah', '@yeah.net', '', null, '网易免费邮箱', '0', '1', '2018-11-30 11:24:10', 'admin', null, null, null, null, null, '55d7666d74df4bb4b36aed08a3a5bad1');
INSERT INTO `base_dictionarydata` VALUES ('3d25701d11764ed6b6b81e163b767e96', '037ba904515348eaad1c4bd462fc80a6', '人事管理', 'personnelManage', 'RSGL', null, '', '0', '1', '2020-09-21 02:02:43', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '037ba904515348eaad1c4bd462fc80a6');
INSERT INTO `base_dictionarydata` VALUES ('3eb5283bd3d544459f94acb7c36971e3', '65e7917344fa460e8c487e45bbbab26f', '默认', '1', 'MR', null, '', '0', '1', '2020-08-04 14:46:24', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '65e7917344fa460e8c487e45bbbab26f');
INSERT INTO `base_dictionarydata` VALUES ('3f626c31a97b48a7934603665ab22533', '0', '一年结算', 'Oneyear\'sknot', 'YNJS', null, null, '0', '1', '2018-07-10 10:25:32', 'admin', '2018-07-10 10:54:21', 'admin', null, null, null, '1143c18426014419a35aa1fa97685c9c');
INSERT INTO `base_dictionarydata` VALUES ('3F9F5BF6-99B8-4342-933A-5A36528D9A3D', '4d885793-2fbd-49ce-9b0e-aa709a71b2d0', '户外/拓展', '户外/拓展', 'HWTZ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('40472c04faa9498bba7b4e11851cf6df', '0', '彝族', '彝族', 'YZ', null, null, '0', '1', '2017-10-24 16:00:35', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('4307b5cd9b7545faac4f927bd091e48f', '7866376d5f694d4d851c7164bd00ebfc', '台胞证', '6', 'TBZ', null, '', '0', '1', '2020-08-08 15:34:47', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '7866376d5f694d4d851c7164bd00ebfc');
INSERT INTO `base_dictionarydata` VALUES ('43347e3055f2498d83e7201be5a243f8', '0', '门巴族', '门巴族', 'MBZ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('43495503d3404a7d95d46c31a0926298', '0', '资产管理', '9', 'ZCGL', null, null, '0', '1', '2020-05-16 17:04:50', 'admin', '2020-05-16 17:06:15', 'admin', null, null, null, 'e0bfb7f7a3054e65949ca0f7c136d717');
INSERT INTO `base_dictionarydata` VALUES ('43f84fba72c14c9ea27a126d2c885fac', 'b1474890faaa47e1858ce7b593c8ffd7', '日常工作', 'routine', 'RCGZ', null, '', '0', '1', '2020-09-21 02:22:33', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, 'b1474890faaa47e1858ce7b593c8ffd7');
INSERT INTO `base_dictionarydata` VALUES ('44338471-6e77-4dd0-899d-ea0355c03277', '0', '美食餐饮', '美食餐饮', 'MSCY', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('449a4fbe9f274e2d89e4b54b2db61237', '02ad722fd1914c338d51597236ad2339', '集团门户', '1', 'JTMH', null, '', '1', '1', '2020-11-06 05:58:28', 'admin', '2020-11-06 06:01:18', 'admin', null, null, null, '02ad722fd1914c338d51597236ad2339');
INSERT INTO `base_dictionarydata` VALUES ('44D8F7B7-3BD7-4C0E-B694-0191E49568E8', 'c6edee1e-ae57-4b1e-84e5-4837e24978b8', '非赢利性组织', '非赢利性组织', 'FYLXZZ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('460494d45135470098d86ec750ac3eb9', '0', '线上注册', '线上注册', 'XSZC', null, null, '0', '1', '2019-12-16 17:52:37', 'admin', null, null, null, null, null, 'c9f32bb4eef748998ed57280fe100fb7');
INSERT INTO `base_dictionarydata` VALUES ('4761028a4baa4b27a20bcc6371a7908f', '573c31998bc04a23a769f9a9eff67d00', '费用相关', 'costRelated', 'FYXG', null, '', '0', '1', '2020-09-21 01:57:29', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '573c31998bc04a23a769f9a9eff67d00');
INSERT INTO `base_dictionarydata` VALUES ('48EA619A-6B6C-4185-9C9E-67A0AE4D15CF', '44338471-6e77-4dd0-899d-ea0355c03277', '甜品饮品', '甜品饮品', 'TPYP', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('49d97d47a68e4787b8e46ae41fc2e9fa', '0', '高山族', '高山族', 'GSZ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('49edcc083777443b92fe87bc4fa7b7e6', '7866376d5f694d4d851c7164bd00ebfc', '军人证', '4', 'JRZ', null, '', '0', '1', '2020-08-08 15:34:05', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '7866376d5f694d4d851c7164bd00ebfc');
INSERT INTO `base_dictionarydata` VALUES ('4a2edd85a2fb4ddc900d007b63184ac9', '4173579c31e74a2b8749e65a23e5c957', '人事管理', 'personnelManage', 'RSGL', null, '', '0', '1', '2020-09-21 01:58:48', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '4173579c31e74a2b8749e65a23e5c957');
INSERT INTO `base_dictionarydata` VALUES ('4A2F85F0-C2CD-4F90-83CF-06ABD9A1975C', '614ecab4-845f-4cbc-9ff7-a9968aca6eeb', '工程机械/行业设备/仪器仪表', '工程机械/行业设备/仪器仪表', 'GCJXXYSBYQYB,GCJXHYSBYQYB', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('4a6910aae94e47a989eec9f65992d486', '0', '页面', '2', 'YM', null, '', '1', '1', '2020-08-05 15:22:49', '00ca450b-141b-45d2-b6cb-94d818165543', '2021-01-22 11:48:59', 'admin', null, null, null, 'e3ca5fe13c484a74bf0a4ea69eec1afd');
INSERT INTO `base_dictionarydata` VALUES ('4B00983C-2994-446C-9D4D-8C7D20624D0A', '8e63a940-4e88-461f-b80d-fd3db065bfef', '婚庆摄影', '婚庆摄影', 'HQSY', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('4b33cdb3fda840e5998bc283e3bc37b5', '0', '现金支付', 'Cashpayment', 'XJZF', null, null, '0', '1', '2018-07-10 10:26:37', 'admin', null, null, null, null, null, '1143c18426014419a35aa1fa97685c9c');
INSERT INTO `base_dictionarydata` VALUES ('4bf7ece67371421db99dec76fe652dfb', '4173579c31e74a2b8749e65a23e5c957', '公文管理', 'documentManage', 'GWGL', null, '', '0', '1', '2020-09-21 01:59:19', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '4173579c31e74a2b8749e65a23e5c957');
INSERT INTO `base_dictionarydata` VALUES ('4c3919cc34a342f8bf92e8bc4e7e1756', '765929a127f44a5b80e773d65d58f96c', '人事管理', 'personnelManage', 'RSGL', null, '', '0', '1', '2020-09-21 01:54:38', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '765929a127f44a5b80e773d65d58f96c');
INSERT INTO `base_dictionarydata` VALUES ('4d3328b99ca54c2fbceeb3fa02c8ae35', '0', '@126', '@126.com', '', null, null, '0', '1', '2018-11-30 11:25:55', 'admin', null, null, null, null, null, '55d7666d74df4bb4b36aed08a3a5bad1');
INSERT INTO `base_dictionarydata` VALUES ('4d885793-2fbd-49ce-9b0e-aa709a71b2d0', '0', '旅游/出行', '旅游/出行', 'LYCX,LYCH', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('4e25539385ac4dae9f69232565faf101', 'c7e90b2ceaa647159490f7637f907290', '金融业', '金融业', 'JRY', null, null, '0', '1', '2019-12-16 17:54:15', 'admin', '2020-09-19 10:20:37', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, 'c7e90b2ceaa647159490f7637f907290');
INSERT INTO `base_dictionarydata` VALUES ('4e60b3aa59c44e6f92aff4518f9e1afe', 'b1474890faaa47e1858ce7b593c8ffd7', '默认', '1', 'MRFL', null, '', '0', '1', '2020-08-04 14:41:32', '00ca450b-141b-45d2-b6cb-94d818165543', '2020-08-04 16:39:40', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, 'b1474890faaa47e1858ce7b593c8ffd7');
INSERT INTO `base_dictionarydata` VALUES ('4EF041F9-A5DA-4120-AC7C-95468EACBF1F', 'f2acc9ef-ce0d-4ef1-a17e-c0cc32c7e678', '食品饮料', '食品饮料', 'SPYL', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('527a34ae81984a1981d7158a22adcbdb', '765929a127f44a5b80e773d65d58f96c', 'CRM应用', 'crm', 'CRMYY', null, '', '0', '1', '2020-09-21 01:57:01', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '765929a127f44a5b80e773d65d58f96c');
INSERT INTO `base_dictionarydata` VALUES ('52D968DF-1B38-4FE4-AC9E-D9958A9D3DD8', '27d8cf5a-22e8-4e05-b4d6-5684a564418a', '网络游戏', '网络游戏', 'WLYX,WLYH', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('531fc6f823214c46b5348002690580be', '0', '@tom', '@tom.com', '', null, null, '0', '1', '2018-11-30 11:31:03', 'admin', null, null, null, null, null, '55d7666d74df4bb4b36aed08a3a5bad1');
INSERT INTO `base_dictionarydata` VALUES ('53e1f6f5a97a47539deefd511a1f820a', '1cff2b60fd7a4c9f82273163b956268c', '费用相关', 'costRelated', 'FYXG', null, '', '0', '1', '2020-09-21 02:00:47', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '1cff2b60fd7a4c9f82273163b956268c');
INSERT INTO `base_dictionarydata` VALUES ('53e35ca9ae4b4a2a9e6eb7be5397b9f5', '1cff2b60fd7a4c9f82273163b956268c', '销售管理', 'saleManage', 'XSGL', null, '', '0', '1', '2020-09-21 02:02:00', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '1cff2b60fd7a4c9f82273163b956268c');
INSERT INTO `base_dictionarydata` VALUES ('54404f70f0ae4982b47dcf6249989182', '0', '和龙街', 'helong', 'HLJ', null, '', '0', '1', '2021-09-07 15:49:45', 'admin', null, null, null, null, null, '245da59b3c174bf88ee498e6cc175a2a');
INSERT INTO `base_dictionarydata` VALUES ('548323F5-70A1-4979-9EE7-F30642B0821D', '0fcc751d-9ceb-4767-8755-f0189b239468', '房屋中介', '房屋中介', 'FWZJ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('548fc264a3974f73818bf1662ea139aa', '573c31998bc04a23a769f9a9eff67d00', '公文管理', 'documentManage', 'GWGL', null, '', '0', '1', '2020-09-21 01:57:55', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '573c31998bc04a23a769f9a9eff67d00');
INSERT INTO `base_dictionarydata` VALUES ('54bad287aa084f81b40c6bc35c7f2545', '0', '汉族', '汉族', 'HZ', null, null, '0', '1', '2017-10-24 16:00:35', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('5502f4f4573646c286313b9328efde6a', 'b1474890faaa47e1858ce7b593c8ffd7', 'CRM应用', 'crm', 'CRMYY', null, '', '0', '1', '2020-09-21 02:23:30', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, 'b1474890faaa47e1858ce7b593c8ffd7');
INSERT INTO `base_dictionarydata` VALUES ('5509d55ae8ee43bc8c25ad93eef90d98', '0', '回族', '回族', 'HZ', null, null, '0', '1', '2017-10-24 16:00:35', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('588b2b6c0fa349a79992d011c7c47a62', '0', '苗族', '苗族', 'MZ', null, null, '0', '1', '2017-10-24 16:00:35', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('58ff6fee60284b598296fd92bf56a7d0', '0', '销售管理', '7', 'XSGL', null, null, '0', '1', '2020-05-16 17:04:33', 'admin', '2020-05-16 17:06:05', 'admin', null, null, null, 'e0bfb7f7a3054e65949ca0f7c136d717');
INSERT INTO `base_dictionarydata` VALUES ('5996617D-23EC-4691-B119-C3270ED1645F', '27d8cf5a-22e8-4e05-b4d6-5684a564418a', '互联网服务', '互联网服务', 'HLWFW', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('59b1f65dd1b044ca88c898e1266ad117', '0', '提前一个月', 'month', 'TQYGY', null, '', '0', '1', '2021-08-20 17:49:50', 'admin', null, null, null, null, null, '30a825bd5467498cbf83b96b1cf29529');
INSERT INTO `base_dictionarydata` VALUES ('59B7E4C7-B888-4DA1-8A68-E23F0E25E5C3', 'd9b969ed-9122-4861-9035-c631acf99378', '咖啡厅/茶馆', '咖啡厅/茶馆', 'KFTCG,GFTCG', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('5ad73824ec844c9683c0bf0d78b6bf6a', '7866376d5f694d4d851c7164bd00ebfc', '港澳身份证', '5', 'GATXZ', null, '', '0', '1', '2020-08-08 15:34:30', '00ca450b-141b-45d2-b6cb-94d818165543', '2020-08-11 11:48:10', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, '7866376d5f694d4d851c7164bd00ebfc');
INSERT INTO `base_dictionarydata` VALUES ('5b6759fbcd9247f4b00a1cafb8e39c1b', '0', '仫佬族', '仫佬族', 'MLZ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('5bf517bb-f45b-491b-93c0-25849ffa0a5c', '0', '金融/保险', '金融/保险', 'JRBX', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('5BFA882E-B4DB-43D5-9950-1AAAB021F0BA', '27d8cf5a-22e8-4e05-b4d6-5684a564418a', '点卡/虚拟币', '点卡/虚拟币', 'DQXNB,DKXNB', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('5D1066AB-F366-42D0-B5BB-4BDB86738C3F', '75f1eb60-1258-4747-bfbc-ac66c1724b49', '公关/市场推广', '公关/市场推广', 'GGSCTG,GGSCTA', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('5d8a4b5bd2a64a3e9e66f3c756efd5ef', '7e8127d8a4114ce08468c912696f1500', '扩展', 'extend', 'KZMK', null, '', '0', '1', '2020-08-25 01:34:51', '00ca450b-141b-45d2-b6cb-94d818165543', '2020-08-25 01:36:03', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, '7e8127d8a4114ce08468c912696f1500');
INSERT INTO `base_dictionarydata` VALUES ('5d8fac985643471f8f9869b6378dee7c', '0', '一次性费用', 'once', 'YCXFY', null, '', '0', '1', '2021-08-20 17:52:23', 'admin', null, null, null, null, null, 'e1b6a07bc17647f888aac0734c6f25ea');
INSERT INTO `base_dictionarydata` VALUES ('5e3d5074fb65412aa297861b63eda463', '037ba904515348eaad1c4bd462fc80a6', '日常工作', 'routine', 'RCGZ', null, '', '0', '1', '2020-09-21 02:02:59', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '037ba904515348eaad1c4bd462fc80a6');
INSERT INTO `base_dictionarydata` VALUES ('5E43C907-A735-45AD-81E4-DA078F49D27C', 'c6edee1e-ae57-4b1e-84e5-4837e24978b8', '公共事业', '公共事业', 'GGSY', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('5E51C077-025C-40CC-84D3-40DD426AD19B', '614ecab4-845f-4cbc-9ff7-a9968aca6eeb', '能源/电力/水利', '能源/电力/水利', 'NYDLSL', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('614ecab4-845f-4cbc-9ff7-a9968aca6eeb', '0', '工业/工业品', '工业/工业品', 'GYGYP', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('619A02B0-1FC7-4899-9E35-21179AD897B3', '926023bb-0b74-4bca-8d67-5d7657d0bca7', '新闻媒体/报刊杂志', '新闻媒体/报刊杂志', 'XWMTBKZZ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('61d27ffb3814412da1aca6db26b83f2a', '0', '项目管理', '6', 'XMGL', null, null, '0', '1', '2020-05-16 17:04:26', 'admin', '2020-05-16 17:06:02', 'admin', null, null, null, 'e0bfb7f7a3054e65949ca0f7c136d717');
INSERT INTO `base_dictionarydata` VALUES ('61fe8b6156a743d685d0509226e7a70b', '0', '满族', '满族', 'MZ', null, null, '0', '1', '2017-10-24 16:00:35', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('63098e7544f14661b0c115d86ddcd8a3', '76da95221f894466b7b3dec327b98c8b', '系统首页', 'Home', 'XTSY', null, '', '0', '1', '2020-08-11 17:21:39', '00ca450b-141b-45d2-b6cb-94d818165543', '2020-08-18 17:37:11', 'admin', null, null, null, '76da95221f894466b7b3dec327b98c8b');
INSERT INTO `base_dictionarydata` VALUES ('63e3e64eebfa41e1b900f391de942d11', '7e8127d8a4114ce08468c912696f1500', '系统', 'system', 'XTMK', null, '', '0', '1', '2020-08-25 01:34:28', '00ca450b-141b-45d2-b6cb-94d818165543', '2020-08-25 01:35:58', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, '7e8127d8a4114ce08468c912696f1500');
INSERT INTO `base_dictionarydata` VALUES ('6410a712cd144e2c8ce5e0f153d88c0d', '0', '高中', '3', 'GZ', null, null, '0', '1', '2017-10-24 16:00:35', null, '2017-10-26 17:12:45', null, null, null, null, '6a6d6fb541b742fbae7e8888528baa16');
INSERT INTO `base_dictionarydata` VALUES ('6454738D-DCDF-4A3F-AA4A-4E88AE3C04BB', '75f1eb60-1258-4747-bfbc-ac66c1724b49', '加盟招商', '加盟招商', 'JMZS', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('65704bad82ac4984bb93968333393590', '0', '拉祜族', '拉祜族', 'LHZ', null, null, '0', '1', '2017-10-24 16:00:35', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('66fe49eb250c4f1e822685c9ef520cf8', '237446e245ce403d8062995ea33711cf', '等于', '1', 'DY', null, null, '0', '1', '2017-10-24 16:00:36', null, '2020-08-05 14:05:15', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, '237446e245ce403d8062995ea33711cf');
INSERT INTO `base_dictionarydata` VALUES ('6704c7aed0bd4c7fba91e830dd7c5ffc', '3e2d33e54d554b4b8a33fc41b93f8a2d', 'IT费用', '3', 'ITFY', null, '', '3', '1', '2020-10-31 01:56:59', 'admin', '2021-01-20 11:59:51', 'admin', null, null, null, '3e2d33e54d554b4b8a33fc41b93f8a2d');
INSERT INTO `base_dictionarydata` VALUES ('689DCC5B-5CCC-402F-8054-83D1EE93BE7B', '75f1eb60-1258-4747-bfbc-ac66c1724b49', '工商注册/代理代办', '工商注册/代理代办', 'GSZCDLDB', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('68A6864E-D2D5-4547-AF89-374E511ECB5F', '1b82cfae-98f1-4b54-a3ae-1d1c6ad3f3f6', '固话/宽带', '固话/宽带', 'GHKD', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('68fdb171f5cc4dc5adc28082a7dd8f56', '42b0f46122b041d097f1fe4f2f637a03', '发起流程', '1', 'FQLC', null, '', '0', '1', '2020-08-10 18:59:03', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '42b0f46122b041d097f1fe4f2f637a03');
INSERT INTO `base_dictionarydata` VALUES ('696babc2ceaf4f5bae3705e2b7e6dee9', '0', '基诺族', '基诺族', 'JNZ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('6ae95571b4964f4b82eefc1743d12a52', '65e7917344fa460e8c487e45bbbab26f', '公文管理', 'documentManage', 'GWGL', null, '', '0', '1', '2020-09-21 02:21:10', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '65e7917344fa460e8c487e45bbbab26f');
INSERT INTO `base_dictionarydata` VALUES ('6af22c4cd01f4241a6fccccb2af561ad', '4173579c31e74a2b8749e65a23e5c957', 'CRM应用', 'crm', 'CRMYY', null, '', '0', '1', '2020-09-21 02:00:16', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '4173579c31e74a2b8749e65a23e5c957');
INSERT INTO `base_dictionarydata` VALUES ('6b485ce9e4ee4790812b7c010ddf5d7e', '3e2d33e54d554b4b8a33fc41b93f8a2d', '安全费用', '4', 'AQFY', null, '', '4', '1', '2020-10-31 01:57:14', 'admin', '2021-01-20 12:00:02', 'admin', null, null, null, '3e2d33e54d554b4b8a33fc41b93f8a2d');
INSERT INTO `base_dictionarydata` VALUES ('6c948043dd974e538fd491ac5c933a80', 'b1474890faaa47e1858ce7b593c8ffd7', '费用相关', 'costRelated', 'FYXG', null, '', '0', '1', '2020-09-21 02:22:25', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, 'b1474890faaa47e1858ce7b593c8ffd7');
INSERT INTO `base_dictionarydata` VALUES ('6ca589afca4a4967bdb935a3f80535c3', '0', '周期性费用', 'circle', 'ZQFF', null, '', '0', '1', '2021-08-20 17:51:45', 'admin', '2021-08-20 17:52:07', 'admin', null, null, null, 'e1b6a07bc17647f888aac0734c6f25ea');
INSERT INTO `base_dictionarydata` VALUES ('6dfca8eb2b734a72adc82a29e2cc41f4', '037ba904515348eaad1c4bd462fc80a6', '行政管理', 'administrationManage', 'XZGL', null, '', '0', '1', '2020-09-21 02:03:33', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '037ba904515348eaad1c4bd462fc80a6');
INSERT INTO `base_dictionarydata` VALUES ('6e72a28ad00b4698b6d58dead6e0b277', '0', '小于等于', '5', 'XYDY', null, null, '0', '1', '2017-10-24 16:00:36', null, '2017-10-26 17:17:41', null, null, null, null, '237446e245ce403d8062995ea33711cf');
INSERT INTO `base_dictionarydata` VALUES ('6f408478f4e84521b5529ff4cd03c26c', '0', '@263', '@263.net', '', null, '企业邮箱', '0', '1', '2018-11-30 11:27:44', 'admin', null, null, null, null, null, '55d7666d74df4bb4b36aed08a3a5bad1');
INSERT INTO `base_dictionarydata` VALUES ('6f817b0eb3ef480ea33b38f57159164f', '0', '普通客户', '普通客户', 'PTKH', null, null, '0', '1', '2019-12-16 17:55:50', 'admin', '2019-12-28 15:14:19', 'admin', null, null, null, '87b9d6e3c2dc4c71a06ff6844e92c292');
INSERT INTO `base_dictionarydata` VALUES ('6FEB335E-C083-41B7-B094-69A5C837EE7E', '8e63a940-4e88-461f-b80d-fd3db065bfef', '洗衣房', '洗衣房', 'XYF', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('71d994f0bc344c31b3711a6fa68d4ee6', '0', '服务支持', '3', 'FWZC', null, null, '0', '1', '2020-05-16 17:03:24', 'admin', '2020-05-16 17:05:39', 'admin', null, null, null, 'e0bfb7f7a3054e65949ca0f7c136d717');
INSERT INTO `base_dictionarydata` VALUES ('7230750d07b94aac8a1febec7dd5cd05', '573c31998bc04a23a769f9a9eff67d00', '行政管理', 'administrationManage', 'XZGL', null, '', '0', '1', '2020-09-21 01:58:14', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '573c31998bc04a23a769f9a9eff67d00');
INSERT INTO `base_dictionarydata` VALUES ('72edc1a7525248d1b8f8fc2725b62765', '0', '展会资源', '展会资源', 'ZKZY,ZHZY', null, null, '0', '1', '2019-12-16 17:53:05', 'admin', null, null, null, null, null, 'c9f32bb4eef748998ed57280fe100fb7');
INSERT INTO `base_dictionarydata` VALUES ('730ffc46716345c8aa2765840c3cbda8', '573c31998bc04a23a769f9a9eff67d00', 'CRM应用', 'crm', 'CRMYY', null, '', '0', '1', '2020-09-21 01:58:36', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '573c31998bc04a23a769f9a9eff67d00');
INSERT INTO `base_dictionarydata` VALUES ('744e2f652ad24b7eaa18d00cf134136a', '0', '支付宝支付', 'aliPay', 'ZFBZF', null, '', '2', '1', '2021-09-02 11:13:15', 'admin', null, null, null, null, null, 'e14b3a85a37048c8aa39ca97570fb18c');
INSERT INTO `base_dictionarydata` VALUES ('74E80DA6-85AA-4595-9A4A-DEAFA4B78EBA', 'b5dc651f-1125-4c6c-8114-24e2ef85ed73', '美容/护理', '美容/护理', 'MRHL', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('7501989f1ba94623b44159c9d630858a', '0', '维吾尔族', '维吾尔族', 'WWEZ', null, null, '0', '1', '2017-10-24 16:00:35', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('7584cef8439241a6a06121f655225715', '0', '保安族', '保安族', 'BAZ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('7592795B-5241-4ADA-8A1F-22192DF432CF', '926023bb-0b74-4bca-8d67-5d7657d0bca7', '文化/广电/影视', '文化/广电/影视', 'WHGDYS,WHADYS', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('75f1eb60-1258-4747-bfbc-ac66c1724b49', '0', '商业服务', '商业服务', 'SYFW', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('76A68CC8-7C7C-4E5A-8270-2C937CF5207D', '75f1eb60-1258-4747-bfbc-ac66c1724b49', '会展服务', '会展服务', 'KZFW,HZFW', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('76fc43857d7a4714985f90d4b9c18f2d', '0', '占地面积*基础费用', 'buildingSquare', 'AFWZDMJ', null, '', '3', '1', '2021-08-20 15:15:12', 'admin', '2021-08-22 18:06:33', 'admin', null, null, null, '7a10e1a5fbc348d8892399ed9b1ffe37');
INSERT INTO `base_dictionarydata` VALUES ('780dc4f559584246bb518f4e1d1abbd3', '0', '个人资源', '个人资源', 'GRZY', null, null, '0', '1', '2019-12-16 17:53:10', 'admin', null, null, null, null, null, 'c9f32bb4eef748998ed57280fe100fb7');
INSERT INTO `base_dictionarydata` VALUES ('7a35330c6df54467bdae7f9f1cafe80d', '7866376d5f694d4d851c7164bd00ebfc', '其他', '7', 'QT', null, '', '0', '1', '2020-08-08 15:35:05', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '7866376d5f694d4d851c7164bd00ebfc');
INSERT INTO `base_dictionarydata` VALUES ('7a4efb0cc3eb4afabf0c00990193ecd6', '0', '业务角色', 'ApplicationRole', 'YWJS', null, null, '0', '1', '2017-10-27 10:23:49', null, '2017-10-27 10:24:06', null, null, null, null, '4501f6f26a384757bce12d4c4b03342c');
INSERT INTO `base_dictionarydata` VALUES ('7A5CCD49-9C2E-45D4-A400-CC55874BD591', '8e63a940-4e88-461f-b80d-fd3db065bfef', '鲜花速递', '鲜花速递', 'XHSD', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('7b605786d4b341199ffef35a7f443c54', '4173579c31e74a2b8749e65a23e5c957', '销售管理', 'saleManage', 'XSGL', null, '', '0', '1', '2020-09-21 01:59:28', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '4173579c31e74a2b8749e65a23e5c957');
INSERT INTO `base_dictionarydata` VALUES ('7b8bd3d6fde74402b7b7fee048d9fc93', '0', '@yahoo', '@yahoo.com', '', null, '雅虎邮箱 ', '0', '1', '2018-11-30 11:39:11', 'admin', null, null, null, null, null, '55d7666d74df4bb4b36aed08a3a5bad1');
INSERT INTO `base_dictionarydata` VALUES ('7befdd0b52094700a029379f7f464c5d', '3e2d33e54d554b4b8a33fc41b93f8a2d', '市场费用', '6', 'SCFY', null, '', '0', '1', '2020-10-31 01:57:57', 'admin', null, null, null, null, null, '3e2d33e54d554b4b8a33fc41b93f8a2d');
INSERT INTO `base_dictionarydata` VALUES ('7c3719226af749a598ee7372b1c67ee9', '0', '公司资源', '公司资源', 'GSZY', null, null, '0', '1', '2019-12-16 17:52:59', 'admin', null, null, null, null, null, 'c9f32bb4eef748998ed57280fe100fb7');
INSERT INTO `base_dictionarydata` VALUES ('7c7d4ae2-455c-439b-b8a8-eb484014aa75', '0', '汽车/汽配/租赁', '汽车/汽配/租赁', 'QJQPZL,QCQPZL', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('7d7dd051-6e57-499b-ac88-c019d7c663da', '0', '医疗/整形整容', '医疗/整形整容', 'YLZXZR', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('7dfaa025d5b949268082bfec2b4be2c8', '0', '@189', '@189.cn', '', null, '天翼', '0', '1', '2018-11-30 11:20:13', 'admin', '2018-11-30 11:33:21', 'admin', null, null, null, '55d7666d74df4bb4b36aed08a3a5bad1');
INSERT INTO `base_dictionarydata` VALUES ('7eee315f771a4282838e168603315eb1', '0', '塔塔尔族', '塔塔尔族', 'TTEZ,TDEZ,DTEZ,DDEZ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('802fb04ab5b84aa5bcc5dc53372a271d', '0', '费用相关', 'costRelated', 'FYXG', null, null, '0', '1', '2018-06-25 10:29:12', 'admin', '2018-06-25 10:49:22', 'admin', null, null, null, '507f4f5df86b47588138f321e0b0dac7');
INSERT INTO `base_dictionarydata` VALUES ('803a14a1ba33425da9d3141ffee7f203', '0', '柯尔克孜族', '柯尔克孜族', 'KEKZZ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('81499648-7139-4C49-8E73-E68085B07524', '1b82cfae-98f1-4b54-a3ae-1d1c6ad3f3f6', '话费充值', '话费充值', 'HFCZ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('828eaef66cb14adda9c5c1997dd7bbe2', '0', '藏族', '藏族', 'ZZ,CZ', null, null, '0', '1', '2017-10-24 16:00:35', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('82d9060f011a42dd8488a9feaaef376e', '0', '集体企业', '3', 'JTQY', null, null, '0', '1', '2017-10-25 14:34:51', null, '2017-10-26 17:14:14', null, null, null, null, '9b542177a477488994ce09fff3c93901');
INSERT INTO `base_dictionarydata` VALUES ('84158383-A0D0-45B5-ADF4-8C7AC1A6E042', 'fc4a08ce-9804-4cb1-a4c6-9440ca614b5d', '奢侈品/收藏品', '奢侈品/收藏品', 'SCPSZP,SCPSCP', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('84992f0eab4742ea81a541e2e8789cab', '0', '仡佬族', '仡佬族', 'YLZ,GLZ', null, null, '0', '1', '2017-10-24 16:00:35', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('84ff10b322d74ac3a653e3176724f909', '963255a34ea64a2584c5d1ba269c1fe6', '男', '1', 'N', null, '', '0', '1', '2020-08-15 03:11:35', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '963255a34ea64a2584c5d1ba269c1fe6');
INSERT INTO `base_dictionarydata` VALUES ('8574248ac12444cc99e0416153b4754c', '1cff2b60fd7a4c9f82273163b956268c', 'CRM应用', 'crm', 'CRMYY', null, '', '0', '1', '2020-09-21 02:02:31', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '1cff2b60fd7a4c9f82273163b956268c');
INSERT INTO `base_dictionarydata` VALUES ('85D701EB-9B95-42CC-99D0-C0026DB5EF4B', '4d885793-2fbd-49ce-9b0e-aa709a71b2d0', '旅行社', '旅行社', 'LXS,LHS', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('85F898D1-D467-4DAF-8846-A3307A2EEA53', '75f1eb60-1258-4747-bfbc-ac66c1724b49', '印刷/包装/平面设计', '印刷/包装/平面设计', 'YSBZPMSJ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('866f934d6ee249f2a03d47175e951d54', '0', '物业管理', 'wuye', 'WYGL', null, '', '0', '1', '2021-08-17 02:41:45', 'admin', null, null, null, null, null, '4173579c31e74a2b8749e65a23e5c957');
INSERT INTO `base_dictionarydata` VALUES ('86f44dce0ebc4b0987b1678d7dae12e6', '037ba904515348eaad1c4bd462fc80a6', '销售管理', 'saleManage', 'XSGL', null, '', '0', '1', '2020-09-21 02:03:25', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '037ba904515348eaad1c4bd462fc80a6');
INSERT INTO `base_dictionarydata` VALUES ('88561e70ac6e465290d43512bee10ef4', '0', '重点客户', '重点客户', 'ZDKH,CDKH', null, null, '0', '1', '2019-12-16 17:55:44', 'admin', '2019-12-28 15:14:14', 'admin', null, null, null, '87b9d6e3c2dc4c71a06ff6844e92c292');
INSERT INTO `base_dictionarydata` VALUES ('895BF6CB-479D-4A3C-9FDE-06CDEA600464', '44338471-6e77-4dd0-899d-ea0355c03277', '西餐/日韩料理/异国美食', '西餐/日韩料理/异国美食', 'XCRHLLYGMS', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('8a7aff7d0c2f47be95049f682b631c99', '02ad722fd1914c338d51597236ad2339', '工程门户', '5', 'GCMH', null, '', '5', '1', '2020-11-06 06:02:34', 'admin', null, null, null, null, null, '02ad722fd1914c338d51597236ad2339');
INSERT INTO `base_dictionarydata` VALUES ('8a9f47d9f51e454fb36fce55fb7a63a6', '0', '独龙族', '独龙族', 'DLZ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('8bfe0cd9d0744f3e859a68c1983d3c47', '0', '刷卡', 'cardPay', 'SK', null, '', '4', '1', '2021-09-02 11:14:39', 'admin', '2021-09-02 11:17:04', 'admin', null, null, null, 'e14b3a85a37048c8aa39ca97570fb18c');
INSERT INTO `base_dictionarydata` VALUES ('8CFC9EED-68FD-46B8-B67B-58AEFD9F45AB', 'fc4a08ce-9804-4cb1-a4c6-9440ca614b5d', '电信增值业务', '电信增值业务', 'DXZZYW', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('8e63a940-4e88-461f-b80d-fd3db065bfef', '0', '生活服务', '生活服务', 'SHFW', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('8eea678c942145f99d7878ace0d8657e', '0', '初中', '2', 'CZ', null, null, '0', '1', '2017-10-24 16:00:35', null, '2017-10-26 17:12:41', null, null, null, null, '6a6d6fb541b742fbae7e8888528baa16');
INSERT INTO `base_dictionarydata` VALUES ('8f4875ed87d44df5932bf00171e0514c', '70f970e14fee4879802b63122511032b', '系统表单', '1', 'XTBD', null, '', '0', '1', '2020-08-10 18:11:24', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '70f970e14fee4879802b63122511032b');
INSERT INTO `base_dictionarydata` VALUES ('8f65bbe3bce341c8b3625d93544a044a', '0', '仓库管理', 'warehouseManagement', 'CKGL', null, null, '0', '1', '2018-07-12 11:23:58', 'admin', null, null, null, null, null, '507f4f5df86b47588138f321e0b0dac7');
INSERT INTO `base_dictionarydata` VALUES ('907cbdba92344967a148a374528dee75', '0', '羌族', '羌族', 'QZ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('908f94c264a64c31a1fecccb811179c6', '0', '佤族', '佤族', 'WZ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('90F0F95F-007B-400D-8146-92665AC3B515', '8e63a940-4e88-461f-b80d-fd3db065bfef', '搬家/搬运', '搬家/搬运', 'BJBY', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('926023bb-0b74-4bca-8d67-5d7657d0bca7', '0', '媒体/广告/出版', '媒体/广告/出版', 'MTGGCB,MTAGCB', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('92ff480a4ca04dbe954bf67018fd29bd', 'c7e90b2ceaa647159490f7637f907290', 'IT/通信/电子/互联网', 'IT/通信/电子/互联网', 'TXDZHLW', null, null, '0', '1', '2019-12-16 17:54:07', 'admin', '2020-09-19 10:20:43', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, 'c7e90b2ceaa647159490f7637f907290');
INSERT INTO `base_dictionarydata` VALUES ('93667ec1069c492082fb60dde3ce8d1c', '0', '白族', '白族', 'BZ', null, null, '0', '1', '2017-10-24 16:00:35', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('93b466da9d0448e596ad1489fc6657ab', '765929a127f44a5b80e773d65d58f96c', '合同管理', 'contractManage', 'HTGL', null, '', '0', '1', '2020-09-21 01:55:52', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '765929a127f44a5b80e773d65d58f96c');
INSERT INTO `base_dictionarydata` VALUES ('93d0b9bc44a44eef94dbbe6a11dc15c5', 'e3ca5fe13c484a74bf0a4ea69eec1afd', '目录', '1', 'ML', null, '', '0', '1', '2020-08-05 15:22:39', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, 'e3ca5fe13c484a74bf0a4ea69eec1afd');
INSERT INTO `base_dictionarydata` VALUES ('94D20938-8724-40CE-806A-FB61DDAB3FBC', '5bf517bb-f45b-491b-93c0-25849ffa0a5c', '投资/证券', '投资/证券', 'TZZX,TZZQ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('950e9a55f1db43ada961d8fb622d22d1', '0', '@outlook', '@outlook.com', '', null, null, '0', '1', '2018-11-30 11:25:03', 'admin', null, null, null, null, null, '55d7666d74df4bb4b36aed08a3a5bad1');
INSERT INTO `base_dictionarydata` VALUES ('957488736a224f139f1e79f3021b7326', '0', '人事管理', '5', 'RSGL', null, null, '0', '1', '2020-05-16 17:04:03', 'admin', '2020-05-16 17:05:55', 'admin', null, null, null, 'e0bfb7f7a3054e65949ca0f7c136d717');
INSERT INTO `base_dictionarydata` VALUES ('972acb74d15e493aae4f471e16518bef', '0', '人事管理', 'personnelManage', 'RSGL', null, null, '0', '1', '2018-01-17 11:50:20', 'admin', '2018-06-25 14:06:57', 'admin', null, null, null, '507f4f5df86b47588138f321e0b0dac7');
INSERT INTO `base_dictionarydata` VALUES ('97337AB5-E9FF-41E6-B963-0A4768B32611', 'd9b969ed-9122-4861-9035-c631acf99378', '会馆会所', '会馆会所', 'KGKS,KGHS,HGKS,HGHS', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('9828f30dd8b5488a968ac048836dcb77', '0', '土族', '土族', 'TZ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('984202e45923443387bde62ab1830ab9', '037ba904515348eaad1c4bd462fc80a6', 'CRM应用', 'crm', 'CRMYY', null, '', '0', '1', '2020-09-21 02:04:01', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '037ba904515348eaad1c4bd462fc80a6');
INSERT INTO `base_dictionarydata` VALUES ('98471961858e4138b36d949d524e8ccd', '3e2d33e54d554b4b8a33fc41b93f8a2d', '其他费用', '7', 'QTFY', null, '', '0', '1', '2020-10-31 01:58:12', 'admin', null, null, null, null, null, '3e2d33e54d554b4b8a33fc41b93f8a2d');
INSERT INTO `base_dictionarydata` VALUES ('9875dce3e50e44c09cea293e02cd9b7b', 'c7e90b2ceaa647159490f7637f907290', '房地产', '房地产', 'FDC', null, null, '0', '1', '2019-12-16 17:54:21', 'admin', '2020-09-19 10:20:29', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, 'c7e90b2ceaa647159490f7637f907290');
INSERT INTO `base_dictionarydata` VALUES ('998ce700fa2449b9a34ff07097e0798b', '0', '布依族', '布依族', 'BYZ', null, null, '0', '1', '2017-10-24 16:00:35', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('9b077e268d4f44e6baa29bb40345f422', '0', '项目管理', 'projectManage', 'XMGL', null, null, '0', '1', '2018-06-25 10:31:18', 'admin', '2018-06-25 14:06:30', 'admin', null, null, null, '507f4f5df86b47588138f321e0b0dac7');
INSERT INTO `base_dictionarydata` VALUES ('9b5ae7aa433f4cdf8045d467956b9ea6', '7866376d5f694d4d851c7164bd00ebfc', '驾照证', '2', 'JZZ', null, '', '0', '1', '2020-08-08 15:33:42', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '7866376d5f694d4d851c7164bd00ebfc');
INSERT INTO `base_dictionarydata` VALUES ('9b78f178da8448b298bc534e229386b5', '0', '高职', '5', 'GZ', null, null, '0', '1', '2017-10-24 16:00:35', null, '2017-10-26 17:12:57', null, null, null, null, '6a6d6fb541b742fbae7e8888528baa16');
INSERT INTO `base_dictionarydata` VALUES ('9baafa488ff447a0a5ae602ace2c1241', '65e7917344fa460e8c487e45bbbab26f', '合同管理', 'contractManage', 'HTGL', null, '', '0', '1', '2020-09-21 02:21:00', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '65e7917344fa460e8c487e45bbbab26f');
INSERT INTO `base_dictionarydata` VALUES ('9bc40d305fc04acaac514d98c1a182d9', '0', '招商资源', '招商资源', 'ZSZY', null, null, '0', '1', '2019-12-16 17:52:53', 'admin', null, null, null, null, null, 'c9f32bb4eef748998ed57280fe100fb7');
INSERT INTO `base_dictionarydata` VALUES ('9C7AA17D-4682-40C9-A9B6-AC14EE7D8A54', '5bf517bb-f45b-491b-93c0-25849ffa0a5c', '典当/抵押/信贷', '典当/抵押/信贷', 'DDDYXD', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('9d46e7f3729843ef8dc35f9987f7f2fe', '0', '现金', 'cash', 'XJ', null, '', '0', '1', '2021-09-02 11:14:01', 'admin', '2021-09-29 17:50:49', 'admin', null, null, null, 'e14b3a85a37048c8aa39ca97570fb18c');
INSERT INTO `base_dictionarydata` VALUES ('9D8266BD-10A3-4A88-8131-51D2C57C3519', 'fc4a08ce-9804-4cb1-a4c6-9440ca614b5d', '工艺品/玩具/珠宝', '工艺品/玩具/珠宝', 'GYPWJZB', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('9d9c76c1bbb44fea9402e1c90801191f', '0', '畲族', '畲族', 'SZ', null, null, '0', '1', '2017-10-24 16:00:35', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('9e9205fb43da4b88a24bb1eebd626ff0', '0', '销售管理', 'saleManage', 'XSGL', null, null, '0', '1', '2018-06-25 10:30:53', 'admin', '2018-06-25 14:06:35', 'admin', null, null, null, '507f4f5df86b47588138f321e0b0dac7');
INSERT INTO `base_dictionarydata` VALUES ('a03a50843fac42fe971d0c3f3a1d4d45', '0', '部门', 'department', 'BM', null, null, '0', '1', '2017-10-24 16:00:36', null, '2017-10-26 17:14:00', null, null, null, null, '9aba44dfc8dc481a9346b9ce38a0735e');
INSERT INTO `base_dictionarydata` VALUES ('a0a14397308d464a8d34f2ea32243ca9', '02ad722fd1914c338d51597236ad2339', '外部门户', '6', 'WBMH', null, '', '6', '1', '2020-11-06 06:02:47', 'admin', null, null, null, null, null, '02ad722fd1914c338d51597236ad2339');
INSERT INTO `base_dictionarydata` VALUES ('a13874e5d3924b7e8270f1487cd04fe1', '0', '阿昌族', '阿昌族', 'ACZ,ECZ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('a1ba2c0db7d44bd7902ffc508e70e3bf', '0', '@qq', '@qq.com', '', null, 'QQ邮箱', '0', '1', '2018-11-30 11:20:42', 'admin', null, null, null, null, null, '55d7666d74df4bb4b36aed08a3a5bad1');
INSERT INTO `base_dictionarydata` VALUES ('a38b8d5c43294b7e97c04d06a7e904ff', '0', '博士及以上', '8', 'BSJYS', null, null, '0', '1', '2017-10-24 16:00:35', null, '2018-03-21 16:47:13', 'admin', null, null, null, '6a6d6fb541b742fbae7e8888528baa16');
INSERT INTO `base_dictionarydata` VALUES ('a38f2c7ea51b49e4ba3bcf9df8ea2394', '038d293fca544c86afa3f2bc65421fd7', '公海客户', '4', 'GHKH', null, '', '0', '1', '2020-11-06 06:43:49', 'admin', null, null, null, null, null, '038d293fca544c86afa3f2bc65421fd7');
INSERT INTO `base_dictionarydata` VALUES ('a4e512091b274d71b44a240d9179a263', '0', '字典', '4', 'ZD', null, '', '2', '1', '2020-08-05 15:23:10', '00ca450b-141b-45d2-b6cb-94d818165543', '2021-01-22 11:49:20', 'admin', null, null, null, 'e3ca5fe13c484a74bf0a4ea69eec1afd');
INSERT INTO `base_dictionarydata` VALUES ('a544571372e94cc6bf7107cd70758dc8', '0', '提前一天', 'day', 'TQYT', null, '', '0', '1', '2021-08-20 17:50:27', 'admin', null, null, null, null, null, '30a825bd5467498cbf83b96b1cf29529');
INSERT INTO `base_dictionarydata` VALUES ('a6312e14b9da4f3ba7d28630f3bfbbb0', '0', '@sohu', '@sohu.com', '', null, '搜狐', '0', '1', '2018-11-30 11:21:42', 'admin', null, null, null, null, null, '55d7666d74df4bb4b36aed08a3a5bad1');
INSERT INTO `base_dictionarydata` VALUES ('a65b1569f0d64fa2870699d65e89901e', '0', '合同费用', 'contractFee', 'HTFY', null, '', '2', '1', '2021-09-10 18:14:04', 'admin', null, null, null, null, null, '364e4a28d15640559122865180f98cfc');
INSERT INTO `base_dictionarydata` VALUES ('a745d425adbb4321880817661cae8910', '7866376d5f694d4d851c7164bd00ebfc', '身份证', '1', 'SFZ', null, '', '0', '1', '2020-08-08 15:33:19', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '7866376d5f694d4d851c7164bd00ebfc');
INSERT INTO `base_dictionarydata` VALUES ('A99C5405-DC2A-440D-A5E3-6B0A8C6026DB', '4d885793-2fbd-49ce-9b0e-aa709a71b2d0', '宾馆/酒店', '宾馆/酒店', 'BGJD', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('a9eb48bfaa434e4ca542a88c3bca9e1d', '0', '@aliyun', '@aliyun.com', '', null, '阿里邮箱', '0', '1', '2018-11-30 11:35:23', 'admin', '2018-11-30 11:35:41', 'admin', null, null, null, '55d7666d74df4bb4b36aed08a3a5bad1');
INSERT INTO `base_dictionarydata` VALUES ('ABA8D38C-7B37-4C32-A7C9-4DBB33A4B413', '614ecab4-845f-4cbc-9ff7-a9968aca6eeb', '环保/废料回收', '环保/废料回收', 'HBFLHS', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('ABE7D855-FA7A-4FF3-B39A-B4999B99C59E', '27d8cf5a-22e8-4e05-b4d6-5684a564418a', '计算机服务/维修', '计算机服务/维修', 'JSJFWWX', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('ad4039ce0d4e4017ae7c917c53ffbcc8', '0', '土家族', '土家族', 'TJZ', '0', null, '0', '1', '2017-10-24 16:00:35', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('ad9147d3de4a41a3b384787e57800264', '65e7917344fa460e8c487e45bbbab26f', 'CRM应用', 'crm', 'CRMYY', null, '', '0', '1', '2020-09-21 02:21:49', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '65e7917344fa460e8c487e45bbbab26f');
INSERT INTO `base_dictionarydata` VALUES ('ADF3DA95-2674-4830-BD9B-14043451C09F', '614ecab4-845f-4cbc-9ff7-a9968aca6eeb', '化工/肥料/农药', '化工/肥料/农药', 'HGFLNY', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('AEF24D19-1B7A-4ADB-85FF-7B974C8B0297', '614ecab4-845f-4cbc-9ff7-a9968aca6eeb', '电工/电气/开关/线缆', '电工/电气/开关/线缆', 'DGDQKGXL', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('AF6172C8-DA14-419D-84F9-6CB1D1A5B231', '614ecab4-845f-4cbc-9ff7-a9968aca6eeb', '五金/工具', '五金/工具', 'WJGJ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('b0f9f27b34d54ccea3bb5c7e4b5e5f34', '0', '乌孜别克族', '乌孜别克族', 'WZBKZ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('b12b07c35a1f4591b64015c012aa0129', '0', '蒙古族', '蒙古族', 'MGZ', null, null, '0', '1', '2017-10-24 16:00:35', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('b16664b364414793b310b4cc56dacd5a', '0', '德昂族', '德昂族', 'DAZ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('b1cd608c85d74975b17bf851fa4ff6f3', '765929a127f44a5b80e773d65d58f96c', '销售管理', 'saleManage', 'XSGL', null, '', '0', '1', '2020-09-21 01:56:24', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '765929a127f44a5b80e773d65d58f96c');
INSERT INTO `base_dictionarydata` VALUES ('B1F6AE91-F128-4E3D-9D5D-4FF08420682D', 'f2acc9ef-ce0d-4ef1-a17e-c0cc32c7e678', '化妆品/护肤品', '化妆品/护肤品', 'HZPHFP', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('b2551e8601624347be5d1e5592bf33a1', '02ad722fd1914c338d51597236ad2339', '财务门户', '4', 'CWMH', null, '', '4', '1', '2020-11-06 06:02:20', 'admin', null, null, null, null, null, '02ad722fd1914c338d51597236ad2339');
INSERT INTO `base_dictionarydata` VALUES ('B2630C4A-ED8C-4147-BAED-5BD0BCA4E5F1', '8e63a940-4e88-461f-b80d-fd3db065bfef', '家政服务', '家政服务', 'JZFW', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('b28702767ac5415e97dea6ed57b63039', '0', '@foxmail', '@foxmail.com', '', null, null, '0', '1', '2018-11-30 11:29:45', 'admin', null, null, null, null, null, '55d7666d74df4bb4b36aed08a3a5bad1');
INSERT INTO `base_dictionarydata` VALUES ('b36e2f4e3508471da4e3beb4a846e480', '0', '日常工作', '1', 'RCGZ', null, null, '0', '1', '2020-05-16 17:04:12', 'admin', '2020-05-16 17:05:51', 'admin', null, null, null, 'e0bfb7f7a3054e65949ca0f7c136d717');
INSERT INTO `base_dictionarydata` VALUES ('b3b1273878104dc18cba8bcfa6b21e43', '037ba904515348eaad1c4bd462fc80a6', '仓库管理', 'warehouseManagement', 'CKGL', null, '', '0', '1', '2020-09-21 02:03:46', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '037ba904515348eaad1c4bd462fc80a6');
INSERT INTO `base_dictionarydata` VALUES ('b3d9ab29078c4f548751692fc1121b6f', '0', '已出租', 'rented', 'YCZ', null, '', '0', '1', '2021-08-17 02:21:09', 'admin', null, null, null, null, null, 'f205aaa2883a42dc820f253c76db054a');
INSERT INTO `base_dictionarydata` VALUES ('B4A01EC3-8EB3-479D-AB60-FBB2A9E13BA8', '0fcc751d-9ceb-4767-8755-f0189b239468', '建材/工程', '建材/工程', 'JCGC', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('b53c34f826354b028036346e63a69ffc', '0', '其他', '10', 'QT,JT', null, null, '0', '1', '2020-05-16 17:04:57', 'admin', '2020-05-16 17:06:22', 'admin', null, null, null, 'e0bfb7f7a3054e65949ca0f7c136d717');
INSERT INTO `base_dictionarydata` VALUES ('b55a07a8ab49451ea67f86ba068b730b', 'b1474890faaa47e1858ce7b593c8ffd7', '测试分类', 'test', 'CSFL', null, '', '0', '1', '2020-08-31 11:29:15', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, 'b1474890faaa47e1858ce7b593c8ffd7');
INSERT INTO `base_dictionarydata` VALUES ('b5dc651f-1125-4c6c-8114-24e2ef85ed73', '0', '美容/保健', '美容/保健', 'MRBJ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('B5F754A5-9598-41E1-AA17-B6223DC144BE', '75f1eb60-1258-4747-bfbc-ac66c1724b49', '人力资源/财会', '人力资源/财会', 'RLZYCK,RLZYCH', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('b5fea94707c044a09370af3af2e1e53b', '765929a127f44a5b80e773d65d58f96c', '公文管理', 'documentManage', 'GWGL', null, '', '0', '1', '2020-09-21 01:56:13', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '765929a127f44a5b80e773d65d58f96c');
INSERT INTO `base_dictionarydata` VALUES ('b620b397f3ec44f083b0df9fbc2f3ba7', '0', '本科', '6', 'BK', null, null, '0', '1', '2017-10-24 16:00:35', null, '2017-10-26 17:13:03', null, null, null, null, '6a6d6fb541b742fbae7e8888528baa16');
INSERT INTO `base_dictionarydata` VALUES ('b6d980d0264041a585d47203f443af4f', '037ba904515348eaad1c4bd462fc80a6', '公文管理', 'documentManage', 'GWGL', null, '', '0', '1', '2020-09-21 02:03:17', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '037ba904515348eaad1c4bd462fc80a6');
INSERT INTO `base_dictionarydata` VALUES ('b6d9b734543d469c973759bea4fe4de0', '0', '包含', '7', 'BH', null, null, '0', '1', '2017-10-24 16:00:36', null, '2017-10-26 17:17:17', null, null, null, null, '237446e245ce403d8062995ea33711cf');
INSERT INTO `base_dictionarydata` VALUES ('b8e5e466e9654e70b9b4f3c53722f59f', '765929a127f44a5b80e773d65d58f96c', '行政管理', 'administrationManage', 'XZGL', null, '', '0', '1', '2020-09-21 01:56:34', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '765929a127f44a5b80e773d65d58f96c');
INSERT INTO `base_dictionarydata` VALUES ('BCA3C4EB-9545-4A2D-94CF-CFFADC2B4EE3', '926023bb-0b74-4bca-8d67-5d7657d0bca7', '图书/音像', '图书/音像', 'TSYX', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('BCAC8366-A51D-4595-B50F-59DBC552CECD', '614ecab4-845f-4cbc-9ff7-a9968aca6eeb', '照明工业', '照明工业', 'ZMGY', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('bcf16235c3a845ee89b77de0dc2d4e5e', '038d293fca544c86afa3f2bc65421fd7', '预签约客户', '3', 'YQYKH', null, '', '0', '1', '2020-11-06 06:43:38', 'admin', null, null, null, null, null, '038d293fca544c86afa3f2bc65421fd7');
INSERT INTO `base_dictionarydata` VALUES ('bcf56b39e6bd4e31ae1621f839fa8b6c', '0', '傈僳族', '傈僳族', 'LSZ', null, null, '0', '1', '2017-10-24 16:00:35', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('be3c621bd9a94e87acbc6f9d999658e6', '65e7917344fa460e8c487e45bbbab26f', '销售管理', 'saleManage', 'XSGL', null, '', '0', '1', '2020-09-21 02:21:18', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '65e7917344fa460e8c487e45bbbab26f');
INSERT INTO `base_dictionarydata` VALUES ('be6b516477ed41dab4d0777336e58ddf', '0', '日常工作', 'routine', 'RCGZ', null, null, '0', '1', '2018-06-25 10:29:24', 'admin', '2018-06-25 10:49:40', 'admin', null, null, null, '507f4f5df86b47588138f321e0b0dac7');
INSERT INTO `base_dictionarydata` VALUES ('c1349445e473436c8148bf1fced9b566', '1e31cad8cf5448a9859d8551015eb6f0', '男', '1', 'N', null, '', '0', '1', '2020-11-04 06:48:43', 'admin', null, null, null, null, null, '1e31cad8cf5448a9859d8551015eb6f0');
INSERT INTO `base_dictionarydata` VALUES ('c3cec71872db40189fb4cc3d1c41dd3a', '765929a127f44a5b80e773d65d58f96c', '费用相关', 'costRelated', 'FYXG', null, '', '0', '1', '2020-09-21 01:55:29', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '765929a127f44a5b80e773d65d58f96c');
INSERT INTO `base_dictionarydata` VALUES ('C48852B0-CE48-49C3-82FB-3267F513E91B', 'fc4a08ce-9804-4cb1-a4c6-9440ca614b5d', '服装服饰', '服装服饰', 'FZFS', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('C4BD8743-ADCF-42EC-901D-44485F1B242A', '8e63a940-4e88-461f-b80d-fd3db065bfef', '美发', '美发', 'MF', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('c5954708b89649258db94a016ec05ff8', '573c31998bc04a23a769f9a9eff67d00', '人事管理', 'personnelManage', 'RSGL', null, '', '0', '1', '2020-09-21 01:54:52', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '573c31998bc04a23a769f9a9eff67d00');
INSERT INTO `base_dictionarydata` VALUES ('c6671d35870949979c032fccae5933da', '02ad722fd1914c338d51597236ad2339', '其他门户', '0', 'QTMH', null, '', '0', '1', '2020-11-06 05:58:53', 'admin', null, null, null, null, null, '02ad722fd1914c338d51597236ad2339');
INSERT INTO `base_dictionarydata` VALUES ('c67213ea412547b5a46a89bc25fabb9a', 'c7e90b2ceaa647159490f7637f907290', '运输/物流', '运输/物流', 'YSWL', null, null, '0', '1', '2019-12-16 17:54:44', 'admin', '2020-09-19 10:20:09', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, 'c7e90b2ceaa647159490f7637f907290');
INSERT INTO `base_dictionarydata` VALUES ('c6edee1e-ae57-4b1e-84e5-4837e24978b8', '0', '政府机关/社会组织', '政府机关/社会组织', 'ZFJGSKZZ,ZFJGSHZZ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('c7199f50afaa46069be39644a7b602c1', '0', '珞巴族', '珞巴族', 'LBZ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('C7469619-5331-42D8-B625-2D75BA69A59C', '5bf517bb-f45b-491b-93c0-25849ffa0a5c', '银行/保险', '银行/保险', 'YXBX,YHBX', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('c92985dbcdd648c6a98b272a8f09698e', '0', '服务支持', 'serviceSupport', 'FWZC', null, null, '0', '1', '2018-06-25 10:30:19', 'admin', '2018-06-25 14:04:26', 'admin', null, null, null, '507f4f5df86b47588138f321e0b0dac7');
INSERT INTO `base_dictionarydata` VALUES ('ca46924df4a14a89bdec649262c646de', '0', '@21cn', '@21cn.com', '', null, null, '0', '1', '2018-11-30 11:23:07', 'admin', null, null, null, null, null, '55d7666d74df4bb4b36aed08a3a5bad1');
INSERT INTO `base_dictionarydata` VALUES ('CC83B3E0-4E70-4910-B39D-4758A89C858F', '7c7d4ae2-455c-439b-b8a8-eb484014aa75', '4S店/汽车经销商', '4S店/汽车经销商', 'DQJJXS,DQCJXS', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('cc899471d3524b25b7a3cede29bd4710', '0', '转介绍', '转介绍', 'ZJS', null, null, '0', '1', '2019-12-16 17:52:30', 'admin', null, null, null, null, null, 'c9f32bb4eef748998ed57280fe100fb7');
INSERT INTO `base_dictionarydata` VALUES ('ccb97b703a8149bcb2bd0fd626632f93', '0', '功能', '3', 'GN', null, '', '3', '1', '2020-08-05 15:22:59', '00ca450b-141b-45d2-b6cb-94d818165543', '2021-01-22 11:49:34', 'admin', null, null, null, 'e3ca5fe13c484a74bf0a4ea69eec1afd');
INSERT INTO `base_dictionarydata` VALUES ('cde0f545147a4f118d33c69651fac4c3', '0', '电话咨询', '电话咨询', 'DHZX', null, null, '0', '1', '2019-12-16 17:53:16', 'admin', null, null, null, null, null, 'c9f32bb4eef748998ed57280fe100fb7');
INSERT INTO `base_dictionarydata` VALUES ('cdf4cba6ab8c444aa7f4c8442bbb8d3c', '1cff2b60fd7a4c9f82273163b956268c', '公文管理', 'documentManage', 'GWGL', null, '', '0', '1', '2020-09-21 02:01:50', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '1cff2b60fd7a4c9f82273163b956268c');
INSERT INTO `base_dictionarydata` VALUES ('ce51233654ee4d1890c7c081a04da2be', '0', '资产管理', 'assetsManage', 'ZCGL', null, null, '0', '1', '2018-06-25 10:30:35', 'admin', '2018-06-25 14:06:47', 'admin', null, null, null, '507f4f5df86b47588138f321e0b0dac7');
INSERT INTO `base_dictionarydata` VALUES ('CEDE4DDD-B946-46E7-87CF-59A393DCED36', '614ecab4-845f-4cbc-9ff7-a9968aca6eeb', '采掘/冶炼/金属', '采掘/冶炼/金属', 'CJYLJZ,CJYLJS', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('cf5b616c15ed40c3bfbf59d4f6e8b7fb', '0', '合伙企业', '4', 'HHQY,GHQY', null, null, '0', '1', '2017-10-25 14:36:13', null, '2017-10-26 17:14:18', null, null, null, null, '9b542177a477488994ce09fff3c93901');
INSERT INTO `base_dictionarydata` VALUES ('d06778318f894c4b914050601897effe', '963255a34ea64a2584c5d1ba269c1fe6', '女', '2', 'N', null, '', '0', '1', '2020-08-15 03:11:43', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '963255a34ea64a2584c5d1ba269c1fe6');
INSERT INTO `base_dictionarydata` VALUES ('D13F172E-080E-4362-862D-088BE89C0B65', '27c38cf2-dc0a-4449-ab87-7eb68f7e425b', '大中小学', '大中小学', 'DZXX', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('d16ec06fe05d423b839ae341f5326053', '0', '大于', '2', 'DY', null, null, '0', '1', '2017-10-24 16:00:36', null, '2017-10-26 17:17:29', null, null, null, null, '237446e245ce403d8062995ea33711cf');
INSERT INTO `base_dictionarydata` VALUES ('d22e88231b604cbbbdf44016a6fa9573', 'c7e90b2ceaa647159490f7637f907290', '贸易', '贸易', 'MY', null, null, '0', '1', '2019-12-16 17:54:32', 'admin', '2020-09-19 10:20:23', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, 'c7e90b2ceaa647159490f7637f907290');
INSERT INTO `base_dictionarydata` VALUES ('d27c63c3630543fea197eefbe553faff', 'b1474890faaa47e1858ce7b593c8ffd7', '行政管理', 'administrationManage', 'XZGL', null, '', '0', '1', '2020-09-21 02:23:14', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, 'b1474890faaa47e1858ce7b593c8ffd7');
INSERT INTO `base_dictionarydata` VALUES ('d30a12225c004651a72cac49b5bb3b05', '573c31998bc04a23a769f9a9eff67d00', '销售管理', 'saleManage', 'XSGL', null, '', '0', '1', '2020-09-21 01:58:04', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '573c31998bc04a23a769f9a9eff67d00');
INSERT INTO `base_dictionarydata` VALUES ('D416CE11-E6A5-4778-AB42-3BDB6262D6AE', '1b82cfae-98f1-4b54-a3ae-1d1c6ad3f3f6', '移动通讯', '移动通讯', 'YDTX', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('d41b37794e1c4e02afa1a102a1ff74bd', '1cff2b60fd7a4c9f82273163b956268c', '行政管理', 'administrationManage', 'XZGL', null, '', '0', '1', '2020-09-21 02:02:10', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '1cff2b60fd7a4c9f82273163b956268c');
INSERT INTO `base_dictionarydata` VALUES ('D4427F85-09BD-4572-BEC0-5297DB690184', '7d7dd051-6e57-499b-ac88-c019d7c663da', '医疗整形', '医疗整形', 'YLZX', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('d44854b5cf304c41badd6afd5cd3f7dd', '0', '转账支付', 'Transferpayment', 'ZZZF', null, null, '0', '1', '2018-07-10 10:27:09', 'admin', null, null, null, null, null, '1143c18426014419a35aa1fa97685c9c');
INSERT INTO `base_dictionarydata` VALUES ('D4C26AA6-8B40-4DD5-BE0B-4EB027958184', '27d8cf5a-22e8-4e05-b4d6-5684a564418a', '计算机软件', '计算机软件', 'JSJRJ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('d4e29600b10e4ab9a688e2cbe702ab7d', '76da95221f894466b7b3dec327b98c8b', '系统登录', 'Login', 'XTDL', null, '', '0', '1', '2020-08-11 17:21:22', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '76da95221f894466b7b3dec327b98c8b');
INSERT INTO `base_dictionarydata` VALUES ('d57025879a6f46fab1578bd325daa3a0', '0', '@163', '@163.com', '', null, '网易邮箱', '0', '1', '2018-11-30 11:18:17', 'admin', null, null, null, null, null, '55d7666d74df4bb4b36aed08a3a5bad1');
INSERT INTO `base_dictionarydata` VALUES ('d59e91fd78194596812ac84a09943de8', '0', '系统角色', 'SystemRole', 'XTJS,JTJS', null, null, '0', '1', '2017-10-27 10:23:39', null, null, null, null, null, null, '4501f6f26a384757bce12d4c4b03342c');
INSERT INTO `base_dictionarydata` VALUES ('d61d47fbb7864eacafe0163873b92b7d', '02ad722fd1914c338d51597236ad2339', '营销门户', '3', 'YXHM', null, '', '3', '1', '2020-11-06 06:01:59', 'admin', '2020-11-06 06:02:08', 'admin', null, null, null, '02ad722fd1914c338d51597236ad2339');
INSERT INTO `base_dictionarydata` VALUES ('d68614ec2be944b0b48be4c5edb9a83f', '0', '布朗族', '布朗族', 'BLZ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('d8df5892e057411ca96f7ad5f7fe7099', '0', '不等于', '6', 'BDY', null, null, '0', '1', '2017-10-24 16:00:36', null, '2017-10-26 17:17:45', null, null, null, null, '237446e245ce403d8062995ea33711cf');
INSERT INTO `base_dictionarydata` VALUES ('D967E161-32D3-4A32-9F99-86A69ECA894E', 'c6edee1e-ae57-4b1e-84e5-4837e24978b8', '其他', '其他', 'QT,JT', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('d9b969ed-9122-4861-9035-c631acf99378', '0', '休闲娱乐', '休闲娱乐', 'XXYY,XXYL', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('da85ed8ca991457bb6b019969ba4adc8', '0', '专技岗位', '2', 'ZJGW', null, '专技岗位指从事专业技术工作，具有相应专业技术水平和能力的工作岗位', '0', '1', '2017-10-26 13:29:17', null, '2017-10-26 17:14:33', null, null, null, null, 'dae93f2fd7cd4df999d32f8750fa6a1e');
INSERT INTO `base_dictionarydata` VALUES ('db3d6642d7f349f19eee700a73aec42d', '765929a127f44a5b80e773d65d58f96c', '日常工作', 'routine', 'RCGZ', null, '', '0', '1', '2020-09-21 01:55:41', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '765929a127f44a5b80e773d65d58f96c');
INSERT INTO `base_dictionarydata` VALUES ('dbe0f98ce3bf42839d84e60bbb2b8c66', '3e2d33e54d554b4b8a33fc41b93f8a2d', '维修费用', '2', 'WXFY', null, '', '2', '1', '2020-10-31 01:56:41', 'admin', '2021-01-20 11:59:24', 'admin', null, null, null, '3e2d33e54d554b4b8a33fc41b93f8a2d');
INSERT INTO `base_dictionarydata` VALUES ('dc96b486bd724586814b37168c97beae', '7866376d5f694d4d851c7164bd00ebfc', '护照', '3', 'HZ', null, '', '0', '1', '2020-08-08 15:33:54', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '7866376d5f694d4d851c7164bd00ebfc');
INSERT INTO `base_dictionarydata` VALUES ('dd961d9d30a5492682a462bd282e8158', '0', '傣族', '傣族', 'DZ', null, null, '0', '1', '2017-10-24 16:00:35', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('decbf5a18cf74bae90b98b3fbe04ec8a', '9c43287481364d348c0ea0d0f64b38be', '大屏图表', '2', 'DP', null, '', '0', '1', '2020-08-10 23:44:41', '00ca450b-141b-45d2-b6cb-94d818165543', '2020-08-10 23:44:51', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, '9c43287481364d348c0ea0d0f64b38be');
INSERT INTO `base_dictionarydata` VALUES ('df251cf8fc464377b11650cbece4eb88', '0', '其他', 'Other', 'QT,JT', null, null, '0', '1', '2018-07-10 10:53:10', 'admin', null, null, null, null, null, '1143c18426014419a35aa1fa97685c9c');
INSERT INTO `base_dictionarydata` VALUES ('DF326EC3-AD05-41E0-B4D3-3382748C700C', 'f2acc9ef-ce0d-4ef1-a17e-c0cc32c7e678', '生活用品', '生活用品', 'SHYP', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('df721d25cb9b4502afb4fe9796f71ac7', '0', '中专', '4', 'ZZ', null, null, '0', '1', '2017-10-24 16:00:35', null, '2017-10-26 17:12:52', null, null, null, null, '6a6d6fb541b742fbae7e8888528baa16');
INSERT INTO `base_dictionarydata` VALUES ('df845894f4d44e54843239bf4a8171f6', '65e7917344fa460e8c487e45bbbab26f', '行政管理', 'administrationManage', 'XZGL', null, '', '0', '1', '2020-09-21 02:21:30', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '65e7917344fa460e8c487e45bbbab26f');
INSERT INTO `base_dictionarydata` VALUES ('e089eb659c894c79acb1cb2f0939d15f', 'b1474890faaa47e1858ce7b593c8ffd7', '仓库管理', 'warehouseManagement', 'CKGL', null, '', '0', '1', '2020-09-21 02:23:21', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, 'b1474890faaa47e1858ce7b593c8ffd7');
INSERT INTO `base_dictionarydata` VALUES ('e0a2b6e477b4499082ef89632063183a', '1cff2b60fd7a4c9f82273163b956268c', '仓库管理', 'warehouseManagement', 'CKGL', null, '', '0', '1', '2020-09-21 02:02:20', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '1cff2b60fd7a4c9f82273163b956268c');
INSERT INTO `base_dictionarydata` VALUES ('e17e977fe98e43d0baabb327f70e6549', '507f4f5df86b47588138f321e0b0dac7', '车辆管理', 'cheliang', 'CLGL', null, '', '0', '1', '2020-10-31 01:18:43', 'admin', null, null, null, null, null, '507f4f5df86b47588138f321e0b0dac7');
INSERT INTO `base_dictionarydata` VALUES ('e25ed4b5bd934c2bb8b4a89ff379992f', 'c7e90b2ceaa647159490f7637f907290', '服务业', '服务业', 'FWY', null, null, '0', '1', '2019-12-16 17:54:49', 'admin', '2020-09-19 10:20:03', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, 'c7e90b2ceaa647159490f7637f907290');
INSERT INTO `base_dictionarydata` VALUES ('e43a773b8a024d109dfb176e99f08a35', '0', '行政管理', 'administrationManage', 'XZGL,HZGL', null, null, '0', '1', '2018-06-25 10:31:26', 'admin', '2018-06-25 14:06:14', 'admin', null, null, null, '507f4f5df86b47588138f321e0b0dac7');
INSERT INTO `base_dictionarydata` VALUES ('e4bb0a72a062405397acdeed43862cda', 'b1474890faaa47e1858ce7b593c8ffd7', '合同管理', 'contractManage', 'HTGL', null, '', '0', '1', '2020-09-21 02:22:45', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, 'b1474890faaa47e1858ce7b593c8ffd7');
INSERT INTO `base_dictionarydata` VALUES ('e669a5d1545e4c39805e22da6df6dfd0', '0', '纳西族', '纳西族', 'NXZ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('E6C0D972-0A2A-48B6-85E8-55F80200C503', '1b82cfae-98f1-4b54-a3ae-1d1c6ad3f3f6', '数码产品', '数码产品', 'SMCP', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('e6d2b9f4b7364a11913e866ff3256d9c', '573c31998bc04a23a769f9a9eff67d00', '日常工作', 'routine', 'RCGZ', null, '', '0', '1', '2020-09-21 01:57:38', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '573c31998bc04a23a769f9a9eff67d00');
INSERT INTO `base_dictionarydata` VALUES ('e73047ef52f44d11b5326bf340981ed1', '0', 'English', 'en-US', '', null, '', '0', '1', '2017-10-24 16:00:36', null, '2020-05-28 17:50:22', 'admin', null, null, null, 'dc6b2542d94b407cac61ec1d59592901');
INSERT INTO `base_dictionarydata` VALUES ('e781a37278e04b1ead88c7e60fee40a7', 'c7e90b2ceaa647159490f7637f907290', '其他', '其他', 'QT,JT', null, null, '0', '1', '2019-12-16 17:55:07', 'admin', '2020-09-19 10:19:38', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, 'c7e90b2ceaa647159490f7637f907290');
INSERT INTO `base_dictionarydata` VALUES ('E86D955D-8E32-4D9E-AF66-87E0D30C5555', '0fcc751d-9ceb-4767-8755-f0189b239468', '楼盘/物业', '楼盘/物业', 'LPWY', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('E8802412-74A7-42BB-B892-F8AAAB779783', '0fcc751d-9ceb-4767-8755-f0189b239468', '室内设计/装潢', '室内设计/装潢', 'SNSJZH', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('e8bfa8ac60d34361b815b48c494f3e06', '0', '合同管理', 'contractManage', 'HTGL,GTGL', null, null, '0', '1', '2018-06-25 10:30:27', 'admin', '2018-06-25 14:06:52', 'admin', null, null, null, '507f4f5df86b47588138f321e0b0dac7');
INSERT INTO `base_dictionarydata` VALUES ('EA8E6EF5-76CA-4E2C-B2B0-D6BAF4ED7F08', '27c38cf2-dc0a-4449-ab87-7eb68f7e425b', '语言培训', '语言培训', 'YYPX', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('EB53529F-71C0-4136-A27B-73A533434D87', '75f1eb60-1258-4747-bfbc-ac66c1724b49', '外包服务', '外包服务', 'WBFW', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('eb5591394bf74e378389279fd63aca65', '037ba904515348eaad1c4bd462fc80a6', '费用相关', 'costRelated', 'FYXG', null, '', '0', '1', '2020-09-21 02:02:50', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '037ba904515348eaad1c4bd462fc80a6');
INSERT INTO `base_dictionarydata` VALUES ('eb789cfd3fe54f1cb9230c6f793897a4', '1cff2b60fd7a4c9f82273163b956268c', '日常工作', 'routine', 'RCGZ', null, '', '0', '1', '2020-09-21 02:00:58', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '1cff2b60fd7a4c9f82273163b956268c');
INSERT INTO `base_dictionarydata` VALUES ('eb8ff4dc825b4cbe8ca212757f35a1b0', '0', '管理岗位', '1', 'GLGW', null, '管理岗位指担负领导啧啧或管理任务的工作岗位', '0', '1', '2017-10-26 13:29:06', null, '2017-10-26 17:14:29', null, null, null, null, 'dae93f2fd7cd4df999d32f8750fa6a1e');
INSERT INTO `base_dictionarydata` VALUES ('EC84E1BB-4DEB-4E71-BEA0-76FB939DFD34', 'f2acc9ef-ce0d-4ef1-a17e-c0cc32c7e678', '烟酒', '烟酒', 'YJ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('ec8d5b687ef84f38ae75162af669f862', 'c7e90b2ceaa647159490f7637f907290', '生产', '生产', 'SC', null, null, '0', '1', '2019-12-16 17:54:38', 'admin', '2020-09-19 10:20:16', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, 'c7e90b2ceaa647159490f7637f907290');
INSERT INTO `base_dictionarydata` VALUES ('ECB00494-3C58-43FF-BA93-8505FB568AB5', '8e63a940-4e88-461f-b80d-fd3db065bfef', '快递/物流/速运', '快递/物流/速运', 'KDWLSY', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('ecf667b572144b068788eaa2f5643376', '0', '@sina', '@sina.com', '', null, '新浪', '0', '1', '2018-11-30 11:21:18', 'admin', '2018-11-30 11:30:30', 'admin', null, null, null, '55d7666d74df4bb4b36aed08a3a5bad1');
INSERT INTO `base_dictionarydata` VALUES ('ed7b686972904272af15dc096cf54c9d', '0', '支票结算', 'Checksettlement', 'ZPJS', null, null, '0', '1', '2018-07-10 10:52:45', 'admin', null, null, null, null, null, '1143c18426014419a35aa1fa97685c9c');
INSERT INTO `base_dictionarydata` VALUES ('ee8b30401c214df4a19e86f63bd8df55', '0', '促销活动', '促销活动', 'CXHD', null, null, '0', '1', '2019-12-16 17:52:03', 'admin', null, null, null, null, null, 'c9f32bb4eef748998ed57280fe100fb7');
INSERT INTO `base_dictionarydata` VALUES ('EECBF1B0-C175-4BAB-A189-5E7DE1632F23', 'b5dc651f-1125-4c6c-8114-24e2ef85ed73', '保健品', '保健品', 'BJP', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('f043454be068470180756c50596e47dd', '0', '公司', 'company', 'GS', null, null, '0', '1', '2017-10-24 16:00:36', null, '2017-10-26 17:13:46', null, null, null, null, '9aba44dfc8dc481a9346b9ce38a0735e');
INSERT INTO `base_dictionarydata` VALUES ('f09bfa62f0b74ede879d98e5bcc21a35', '765929a127f44a5b80e773d65d58f96c', '仓库管理', 'warehouseManagement', 'CKGL', null, '', '0', '1', '2020-09-21 01:56:51', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '765929a127f44a5b80e773d65d58f96c');
INSERT INTO `base_dictionarydata` VALUES ('f10e48fc01024e3fb0ef67bfbf68f787', '0', '非优先客户', '非优先客户', 'FYXKH', null, null, '0', '1', '2019-12-16 17:55:56', 'admin', '2019-12-28 15:14:24', 'admin', null, null, null, '87b9d6e3c2dc4c71a06ff6844e92c292');
INSERT INTO `base_dictionarydata` VALUES ('F18D5175-4200-47C4-B299-3AFBF8216AF9', '926023bb-0b74-4bca-8d67-5d7657d0bca7', '广告', '广告', 'GG,AG', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('f2acc9ef-ce0d-4ef1-a17e-c0cc32c7e678', '0', '日用杂货/家电', '日用杂货/家电', 'RYZHJD', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('f3dc29e8a224486c9f8150f0855336e7', '65e7917344fa460e8c487e45bbbab26f', '人事管理', 'personnelManage', 'RSGL', null, '', '0', '1', '2020-09-21 02:20:31', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '65e7917344fa460e8c487e45bbbab26f');
INSERT INTO `base_dictionarydata` VALUES ('F463A7BE-F5BC-4C9F-8A89-F744C1E1A88E', '7d7dd051-6e57-499b-ac88-c019d7c663da', '制药/生物', '制药/生物', 'ZYSW', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('f4a0897d4f2d4e748a8f55ec7d85a82a', '0', '私营企业', '2', 'SYQY', null, null, '0', '1', '2017-10-25 14:34:42', null, '2017-10-26 17:14:10', null, null, null, null, '9b542177a477488994ce09fff3c93901');
INSERT INTO `base_dictionarydata` VALUES ('f508a8a3bd354d409c245a1adf97a2f1', '573c31998bc04a23a769f9a9eff67d00', '合同管理', 'contractManage', 'HTGL', null, '', '0', '1', '2020-09-21 01:57:46', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '573c31998bc04a23a769f9a9eff67d00');
INSERT INTO `base_dictionarydata` VALUES ('f559976768564145ba38dab1b32a3e53', 'c7e90b2ceaa647159490f7637f907290', '政府', '政府', 'ZF', null, null, '0', '1', '2019-12-16 17:55:01', 'admin', '2020-09-19 10:19:47', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, 'c7e90b2ceaa647159490f7637f907290');
INSERT INTO `base_dictionarydata` VALUES ('f5c79713ba9d414faabbc253f64181ef', 'b1474890faaa47e1858ce7b593c8ffd7', '公文管理', 'documentManage', 'GWGL', null, '', '0', '1', '2020-09-21 02:22:56', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, 'b1474890faaa47e1858ce7b593c8ffd7');
INSERT INTO `base_dictionarydata` VALUES ('f5ebba6dbf47453687b6a10694694aaf', '0', '赫哲族', '赫哲族', 'HZZ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('F61115F6-A7A1-4B04-858E-E976721AAF30', '75f1eb60-1258-4747-bfbc-ac66c1724b49', '办公用品及设备', '办公用品及设备', 'BGYPJSB', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('f64035085f6f4f50864c3aab0fe42d99', '0', '大于等于', '3', 'DYDY', null, null, '0', '1', '2017-10-24 16:00:36', null, '2017-10-26 17:17:33', null, null, null, null, '237446e245ce403d8062995ea33711cf');
INSERT INTO `base_dictionarydata` VALUES ('f64c7def086241d4b253571868ac9e17', '4173579c31e74a2b8749e65a23e5c957', '日常工作', 'routine', 'RCGZ', null, '', '0', '1', '2020-09-21 01:59:04', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '4173579c31e74a2b8749e65a23e5c957');
INSERT INTO `base_dictionarydata` VALUES ('f6881a48b1984a44845a056b3bcdc000', '65e7917344fa460e8c487e45bbbab26f', '日常工作', 'routine', 'RCGZ', null, '', '0', '1', '2020-09-21 02:20:52', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '65e7917344fa460e8c487e45bbbab26f');
INSERT INTO `base_dictionarydata` VALUES ('F6D5FE02-A217-4F90-A820-D5BD710B4FF7', '7d7dd051-6e57-499b-ac88-c019d7c663da', '医疗设备/器械', '医疗设备/器械', 'YLSBQX', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('f8b87124ef2a45758426c7d76afa2c90', '0', '公文管理', 'documentManage', 'GWGL', null, null, '0', '1', '2018-06-25 10:30:46', 'admin', '2018-06-25 14:06:40', 'admin', null, null, null, '507f4f5df86b47588138f321e0b0dac7');
INSERT INTO `base_dictionarydata` VALUES ('f8be14c58efe48d5adc66cbea176bfcd', '0', '鄂伦春族', '鄂伦春族', 'ELCZ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('f8d20d8d3ea24c54bf522dad4652d105', '65e7917344fa460e8c487e45bbbab26f', '仓库管理', 'warehouseManagement', 'CKGL', null, '', '0', '1', '2020-09-21 02:21:40', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null, '65e7917344fa460e8c487e45bbbab26f');
INSERT INTO `base_dictionarydata` VALUES ('f913416000a14ed7b66726cc352219d9', '0', '不包含', '8', 'BBH', null, null, '0', '1', '2017-10-24 16:00:36', null, '2017-10-26 17:17:21', null, null, null, null, '237446e245ce403d8062995ea33711cf');
INSERT INTO `base_dictionarydata` VALUES ('f9bcd7907cc54ca5aad76b3cda0c6351', '0', '固定费用', 'fixed', 'GDFY', null, '', '1', '1', '2021-08-20 15:09:21', 'admin', '2021-08-20 15:10:12', 'admin', null, null, null, '7a10e1a5fbc348d8892399ed9b1ffe37');
INSERT INTO `base_dictionarydata` VALUES ('FB03EBBE-AE85-4394-91EF-DB6D6DC0BBCB', '27c38cf2-dc0a-4449-ab87-7eb68f7e425b', '孕前/婴幼儿教育', '孕前/婴幼儿教育', 'YQYYEJY', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('fb096124a2564dcc84eafc92b771e667', '0', '塔吉克族', '塔吉克族', 'TJKZ,DJKZ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'b6cd65a763fa45eb9fe98e5057693e40');
INSERT INTO `base_dictionarydata` VALUES ('FBA08D44-765D-4462-9782-E4AADB4E8AE3', '7c7d4ae2-455c-439b-b8a8-eb484014aa75', '租车/代驾', '租车/代驾', 'ZJDJ,ZCDJ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('FBE677DD-AB1D-43D0-AF5F-460B2610F58A', '75f1eb60-1258-4747-bfbc-ac66c1724b49', '咨询/顾问', '咨询/顾问', 'ZXGW', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('fc4a08ce-9804-4cb1-a4c6-9440ca614b5d', '0', '鞋服/工艺品/奢侈品', '鞋服/工艺品/奢侈品', 'XFGYPSCP', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('fc8f491b718d4b9f95b0d8639800786b', '0', '车位费用', 'parking', 'CWFY', null, '', '1', '1', '2021-08-20 18:20:58', 'admin', null, null, null, null, null, '364e4a28d15640559122865180f98cfc');
INSERT INTO `base_dictionarydata` VALUES ('FCAD0103-FAA2-4D5C-96AA-D113F307225D', 'f2acc9ef-ce0d-4ef1-a17e-c0cc32c7e678', '家电/小家电/厨卫用具', '家电/小家电/厨卫用具', 'JDXJDCWYJ', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('FD32473F-9151-4A9F-85FF-B1FE95D5B9D4', '8e63a940-4e88-461f-b80d-fd3db065bfef', '维修/疏通', '维修/疏通', 'WXST', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');
INSERT INTO `base_dictionarydata` VALUES ('fd91e368c233442aa046758ae24e9d09', '0', '使用面积*基础费用', 'useSquare', 'AFWSYMJ', null, '', '4', '1', '2021-08-20 15:15:41', 'admin', '2021-08-22 18:07:04', 'admin', null, null, null, '7a10e1a5fbc348d8892399ed9b1ffe37');
INSERT INTO `base_dictionarydata` VALUES ('fda8b268e91244eeb70435ed5f4676da', '0', '@hotmail', '@hotmail.com', '', null, null, '0', '1', '2018-11-30 11:22:40', 'admin', null, null, null, null, null, '55d7666d74df4bb4b36aed08a3a5bad1');
INSERT INTO `base_dictionarydata` VALUES ('fe72535365154b5f82fd7c2f984087f3', '0', 'CRM应用', 'crm', 'YY', null, null, '0', '1', '2019-11-27 18:30:31', 'admin', null, null, null, null, null, '507f4f5df86b47588138f321e0b0dac7');
INSERT INTO `base_dictionarydata` VALUES ('FE7F43BF-75C7-492B-8898-72A40FDDBB95', 'd9b969ed-9122-4861-9035-c631acf99378', 'KTV/酒吧/夜店', 'KTV/酒吧/夜店', 'JBYD', null, null, '0', '1', '2017-10-24 16:00:36', null, null, null, null, null, null, 'd59a3cf65f9847dbb08be449e3feae16');

-- ----------------------------
-- Table structure for `base_dictionarytype`
-- ----------------------------
DROP TABLE IF EXISTS `base_dictionarytype`;
CREATE TABLE `base_dictionarytype` (
  `Id` varchar(50) NOT NULL COMMENT '自然主键',
  `ParentId` varchar(50) DEFAULT NULL COMMENT '上级',
  `FullName` varchar(50) DEFAULT NULL COMMENT '名称',
  `EnCode` varchar(50) DEFAULT NULL COMMENT '编号',
  `IsTree` int(11) DEFAULT NULL COMMENT '树形',
  `Description` longtext COMMENT '描述',
  `SortCode` bigint(20) DEFAULT NULL COMMENT '排序',
  `EnabledMark` int(11) DEFAULT NULL COMMENT '有效标志',
  `CreatorTime` datetime DEFAULT NULL COMMENT '创建时间',
  `CreatorUserId` varchar(50) DEFAULT NULL COMMENT '创建用户',
  `LastModifyTime` datetime DEFAULT NULL COMMENT '修改时间',
  `LastModifyUserId` varchar(50) DEFAULT NULL COMMENT '修改用户',
  `DeleteMark` int(11) DEFAULT NULL COMMENT '删除标志',
  `DeleteTime` datetime DEFAULT NULL COMMENT '删除时间',
  `DeleteUserId` varchar(50) DEFAULT NULL COMMENT '删除用户',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='字典分类';

-- ----------------------------
-- Records of base_dictionarytype
-- ----------------------------
INSERT INTO `base_dictionarytype` VALUES ('02ad722fd1914c338d51597236ad2339', 'efdbdac8d9e04539b85cda898aabde65', '门户设计分类', 'portalDesigner', '0', '', '0', null, '2020-09-21 00:49:02', '00ca450b-141b-45d2-b6cb-94d818165543', '2020-09-21 00:49:42', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('037ba904515348eaad1c4bd462fc80a6', '188f465964644e0f81b8ffc756eefa92', '流程表单分类', 'flowForm', '0', '', '0', null, '2020-09-19 07:04:57', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('038d293fca544c86afa3f2bc65421fd7', '821ddb80b4f14b918f32549f72a81473', '客户类型', 'kehuleixing', '1', '', '0', null, '2020-11-06 06:43:02', 'admin', '2020-11-14 09:14:07', 'admin', null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('1143c18426014419a35aa1fa97685c9c', 'afcc3a0952df4d1bad7d83cc8eb20fbd', '结算方式', 'WFSettlementMethod', '0', '支付', '0', null, '2018-07-10 10:22:34', 'admin', '2020-09-19 06:28:14', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('1274537ff6374a7abe8f7fcb21997d08', '-1', '商铺字典', 'housedict', '0', '', '0', null, '2021-08-17 02:15:07', 'admin', '2021-09-13 14:41:22', null, null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('188f465964644e0f81b8ffc756eefa92', '-1', '代码生成', 'codeGeneration', '1', '', '0', null, '2020-09-19 07:03:45', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('1cff2b60fd7a4c9f82273163b956268c', '188f465964644e0f81b8ffc756eefa92', '移动表单分类', 'appForm', '0', '', '0', null, '2020-09-19 07:04:35', '00ca450b-141b-45d2-b6cb-94d818165543', '2020-10-14 01:41:43', 'admin', null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('237446e245ce403d8062995ea33711cf', 'afcc3a0952df4d1bad7d83cc8eb20fbd', '比较符号', 'Comparison', '0', null, '0', null, '2017-10-20 16:52:32', null, '2020-09-19 06:27:58', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('245da59b3c174bf88ee498e6cc175a2a', '1274537ff6374a7abe8f7fcb21997d08', '商铺区域', 'houseArea', '0', '', '0', null, '2021-08-17 02:17:48', 'admin', '2021-09-13 14:41:04', null, null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('364e4a28d15640559122865180f98cfc', '5dfc1a5ba55e4239843c699f6db5529c', '费用类型', 'type', '0', '', '0', null, '2021-08-20 18:11:45', 'admin', null, null, null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('3e2d33e54d554b4b8a33fc41b93f8a2d', '821ddb80b4f14b918f32549f72a81473', '审批费用类型区分', 'shenpi', '0', '', '0', null, '2020-10-31 01:55:50', 'admin', '2020-11-14 09:14:23', 'admin', null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('4173579c31e74a2b8749e65a23e5c957', '188f465964644e0f81b8ffc756eefa92', '功能表单分类', 'webForm', '0', '', '0', null, '2020-09-19 07:04:11', '00ca450b-141b-45d2-b6cb-94d818165543', '2020-10-14 01:41:49', 'admin', null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('42b0f46122b041d097f1fe4f2f637a03', 'd3fc17551c29461b878eb98baf60552c', '流程引擎类型', 'flowType', '0', '', '0', null, '2020-08-10 18:58:44', '00ca450b-141b-45d2-b6cb-94d818165543', '2020-09-21 00:51:41', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('4501f6f26a384757bce12d4c4b03342c', 'afcc3a0952df4d1bad7d83cc8eb20fbd', '角色分类', 'RoleType', '0', null, '0', null, '2017-10-27 10:19:32', null, '2020-09-19 06:26:11', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('507f4f5df86b47588138f321e0b0dac7', 'd3fc17551c29461b878eb98baf60552c', '流程设计分类', 'WorkFlowCategory', '0', null, '0', null, '2018-01-17 11:49:20', 'admin', '2020-09-21 00:53:29', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('55d7666d74df4bb4b36aed08a3a5bad1', 'afcc3a0952df4d1bad7d83cc8eb20fbd', '邮箱格式', 'Email', '0', null, '0', null, '2018-11-30 11:16:03', 'admin', '2020-09-19 06:27:46', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('573c31998bc04a23a769f9a9eff67d00', 'efdbdac8d9e04539b85cda898aabde65', '移动设计分类', 'appDesigner', '0', '', '0', null, '2020-09-19 07:01:02', '00ca450b-141b-45d2-b6cb-94d818165543', '2020-10-14 01:41:58', 'admin', null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('5dfc1a5ba55e4239843c699f6db5529c', '-1', '收费项字典', 'chargeItem', '0', '', '0', null, '2021-08-20 14:58:51', 'admin', null, null, null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('65e7917344fa460e8c487e45bbbab26f', 'efdbdac8d9e04539b85cda898aabde65', '报表设计分类', 'ReportSort', '0', null, '0', null, '2020-05-16 16:56:35', 'admin', '2020-09-19 07:02:42', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('6a6d6fb541b742fbae7e8888528baa16', 'afcc3a0952df4d1bad7d83cc8eb20fbd', '学历', 'Education', '0', null, '0', null, '2017-10-20 16:51:29', null, '2020-09-19 06:30:00', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('70f970e14fee4879802b63122511032b', 'd3fc17551c29461b878eb98baf60552c', '流程表单类型', 'flowFormType', '0', '', '0', null, '2020-08-10 18:10:40', '00ca450b-141b-45d2-b6cb-94d818165543', '2020-09-21 00:51:21', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('765929a127f44a5b80e773d65d58f96c', 'efdbdac8d9e04539b85cda898aabde65', '功能设计分类', 'webDesign', '0', '', '0', null, '2020-09-19 07:00:42', '00ca450b-141b-45d2-b6cb-94d818165543', '2020-10-14 01:42:05', 'admin', null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('76da95221f894466b7b3dec327b98c8b', 'afcc3a0952df4d1bad7d83cc8eb20fbd', '翻译分类', 'languageType', '1', '', '0', null, '2020-08-11 17:11:25', '00ca450b-141b-45d2-b6cb-94d818165543', '2020-09-19 06:25:01', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('7866376d5f694d4d851c7164bd00ebfc', 'afcc3a0952df4d1bad7d83cc8eb20fbd', '证件类型', 'certificateType', '0', '', '0', null, '2020-08-08 10:08:08', '00ca450b-141b-45d2-b6cb-94d818165543', '2020-09-19 06:27:28', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('7a10e1a5fbc348d8892399ed9b1ffe37', '5dfc1a5ba55e4239843c699f6db5529c', '费用计算公式', 'changeItemFormula', '0', '', '0', null, '2021-08-20 15:04:44', 'admin', '2021-08-20 15:07:19', 'admin', null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('821ddb80b4f14b918f32549f72a81473', '-1', '功能示例', 'FunctionExample', '0', '', '0', null, '2020-11-14 09:13:50', 'admin', null, null, null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('8f38c28a718548a3b3287fa5dd15615a', '-1', '住房字典', 'flatdict', '0', '', '0', null, '2021-09-13 14:40:54', null, null, null, null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('963255a34ea64a2584c5d1ba269c1fe6', 'afcc3a0952df4d1bad7d83cc8eb20fbd', '性别', 'sex', '0', '', '0', null, '2020-08-15 03:10:55', '00ca450b-141b-45d2-b6cb-94d818165543', '2020-09-19 06:59:53', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('9aba44dfc8dc481a9346b9ce38a0735e', 'afcc3a0952df4d1bad7d83cc8eb20fbd', '组织分类', 'OrganizeType', '0', null, '0', null, '2017-10-20 16:51:50', null, '2020-09-19 06:25:41', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('9b542177a477488994ce09fff3c93901', 'afcc3a0952df4d1bad7d83cc8eb20fbd', '企业性质', 'EnterpriseNature', '0', null, '0', null, '2017-10-25 14:33:08', null, '2020-09-19 06:26:57', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('9c43287481364d348c0ea0d0f64b38be', 'afcc3a0952df4d1bad7d83cc8eb20fbd', '接口分类', 'DataInterfaceType', '1', '', '0', null, '2020-08-04 14:44:52', '00ca450b-141b-45d2-b6cb-94d818165543', '2020-09-19 06:25:15', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('afcc3a0952df4d1bad7d83cc8eb20fbd', '-1', '系统管理', 'system', '1', '', '0', null, '2020-09-19 06:23:59', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('b1474890faaa47e1858ce7b593c8ffd7', 'efdbdac8d9e04539b85cda898aabde65', '大屏设计分类', 'dataVType', '0', '', '0', null, '2020-08-04 14:40:32', '00ca450b-141b-45d2-b6cb-94d818165543', '2020-09-19 07:02:56', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('b6cd65a763fa45eb9fe98e5057693e40', 'afcc3a0952df4d1bad7d83cc8eb20fbd', '民族', 'Nation', '0', null, '0', null, '2017-10-20 16:51:38', null, '2020-09-19 06:59:38', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('d3fc17551c29461b878eb98baf60552c', '-1', '工作流程', 'workFlow', '0', '', '0', null, '2020-08-10 18:53:12', '00ca450b-141b-45d2-b6cb-94d818165543', '2020-09-21 03:56:16', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('d59a3cf65f9847dbb08be449e3feae16', 'afcc3a0952df4d1bad7d83cc8eb20fbd', '行业类别', 'IndustryType', '1', null, '0', null, '2017-10-20 16:52:04', null, '2020-09-19 06:26:38', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('dae93f2fd7cd4df999d32f8750fa6a1e', 'afcc3a0952df4d1bad7d83cc8eb20fbd', '岗位分类', 'PositionType', '0', null, '0', null, '2017-10-26 13:28:40', null, '2020-09-19 06:25:56', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('dc6b2542d94b407cac61ec1d59592901', 'afcc3a0952df4d1bad7d83cc8eb20fbd', '系统语言', 'Language', '0', '配置多语言', '0', null, '2017-10-20 16:52:45', null, '2020-09-19 06:24:42', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('e14b3a85a37048c8aa39ca97570fb18c', '5dfc1a5ba55e4239843c699f6db5529c', '支付方式', 'payMethod', '0', '', '0', null, '2021-09-02 11:12:16', 'admin', null, null, null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('e1b6a07bc17647f888aac0734c6f25ea', '5dfc1a5ba55e4239843c699f6db5529c', '费用周期', 'period', '0', '', '0', null, '2021-08-20 17:51:04', 'admin', '2021-08-20 18:06:35', 'admin', null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('e3ca5fe13c484a74bf0a4ea69eec1afd', 'afcc3a0952df4d1bad7d83cc8eb20fbd', '菜单类型', 'menuType', '0', '', '0', null, '2020-08-05 15:22:26', '00ca450b-141b-45d2-b6cb-94d818165543', '2020-09-19 06:27:13', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('efdbdac8d9e04539b85cda898aabde65', '-1', '在线开发', 'onlineDev', '1', '', '0', null, '2020-09-19 07:00:16', '00ca450b-141b-45d2-b6cb-94d818165543', null, null, null, null, null);
INSERT INTO `base_dictionarytype` VALUES ('f205aaa2883a42dc820f253c76db054a', '1274537ff6374a7abe8f7fcb21997d08', '商铺状态', 'houseState', '0', '', '0', null, '2021-08-17 02:19:00', 'admin', '2021-09-13 14:41:14', null, null, null, null);

-- ----------------------------
-- Table structure for `config_fee_alert`
-- ----------------------------
DROP TABLE IF EXISTS `config_fee_alert`;
CREATE TABLE `config_fee_alert` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `fee_id` varchar(50) NOT NULL COMMENT '收费项id',
  `day` int(11) NOT NULL COMMENT '天数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收费项续费提醒';

-- ----------------------------
-- Records of config_fee_alert
-- ----------------------------

-- ----------------------------
-- Table structure for `config_fee_item`
-- ----------------------------
DROP TABLE IF EXISTS `config_fee_item`;
CREATE TABLE `config_fee_item` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `type` varchar(50) NOT NULL COMMENT '收费项分类',
  `name` varchar(100) NOT NULL COMMENT '收费项目名',
  `price` decimal(10,4) NOT NULL COMMENT '单价',
  `num_type` varchar(50) NOT NULL COMMENT '数量',
  `period` int(2) NOT NULL COMMENT '费用周期(月为单位)',
  `formula` varchar(50) NOT NULL COMMENT '费用计算公式',
  `formula_expression` varchar(1000) DEFAULT NULL COMMENT '自定义',
  `generate_type` varchar(50) NOT NULL,
  `late_fee_enable` int(1) NOT NULL COMMENT '是否产生滞纳金',
  `late_fee_days` int(11) DEFAULT NULL COMMENT '滞纳金延迟多久收',
  `late_fee_rate` varchar(255) DEFAULT NULL COMMENT '滞纳金比例',
  `creator_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator_user_id` varchar(50) DEFAULT NULL COMMENT '创建用户',
  `last_modify_user_id` varchar(50) DEFAULT NULL COMMENT '修改用户',
  `last_modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `delete_user_id` varchar(50) DEFAULT NULL COMMENT '删除用户',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `enabled_mark` int(11) NOT NULL COMMENT '有效标志',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收费项';

-- ----------------------------
-- Records of config_fee_item
-- ----------------------------
INSERT INTO `config_fee_item` VALUES ('027e94f0f5c640adbb6466c3b26825a0', 'house', '空调费', '0.2500', 'meter', '1', 'base', '', 'meter', '0', '0', '0', '2021-11-05 16:37:53', null, null, null, null, null, '1');
INSERT INTO `config_fee_item` VALUES ('1334284783299108865', 'house', '抄表水费', '0.4500', 'meter', '1', 'base', '', 'meter', '0', '0', '0', null, null, null, null, null, null, '1');
INSERT INTO `config_fee_item` VALUES ('1334284923695046657', 'temp', '十分士大夫士大夫大师傅发射点发射点发', '0.0000', '', '1', '', '', 'base', '0', '0', '0', null, null, '1', '2020-12-03 13:39:54', '1', '2020-12-03 13:39:54', '0');
INSERT INTO `config_fee_item` VALUES ('1350c189bae64c938ff2ba757a9af9e9', 'house', '公共区域自来水费', '0.0000', 'flat', '1', 'base', '', 'base', '0', '0', '0', '2021-11-05 16:27:17', null, null, null, null, null, '1');
INSERT INTO `config_fee_item` VALUES ('1456542723673780225', 'house', '取暖费', '35.0000', 'building_area', '12', 'base', '', 'base', '0', '0', '0', '2021-11-05 16:43:41', null, null, null, null, null, '1');
INSERT INTO `config_fee_item` VALUES ('1456543121520291842', 'deposit', '装修保证金', '0.0000', '', '1', '', '', 'base', '0', '0', '0', '2021-11-05 16:45:16', null, null, null, null, null, '1');
INSERT INTO `config_fee_item` VALUES ('1456543887689609218', 'house', '装修管理服务费', '200.0000', 'flat', '12', 'base', '', 'base', '0', '0', '0', '2021-11-05 16:48:19', null, null, null, null, null, '1');
INSERT INTO `config_fee_item` VALUES ('1456545461837066241', 'temp', '装修垃圾清运费', '0.0000', '', '1', '', '', 'base', '0', '0', '0', '2021-11-05 16:54:34', null, '1', '2020-12-03 13:43:14', null, null, '1');
INSERT INTO `config_fee_item` VALUES ('1456545687880691714', 'temp', '装修违章罚款', '0.0000', '', '1', '', '', 'base', '0', '0', '0', '2021-11-05 16:55:28', null, '1', '2020-12-03 13:48:08', null, null, '1');
INSERT INTO `config_fee_item` VALUES ('1456545945662615553', 'temp', '物业违章罚款', '0.0000', '', '1', '', '', 'base', '0', '0', '0', '2021-11-05 16:56:29', null, '1', '2020-12-03 13:49:32', null, null, '1');
INSERT INTO `config_fee_item` VALUES ('1456546272025604098', 'temp', '广告费用（公共区域）', '0.0000', '', '1', '', '', 'base', '0', '0', '0', '2021-11-05 16:57:47', null, null, null, null, null, '1');
INSERT INTO `config_fee_item` VALUES ('1456546648464388097', 'house', '维修服务费', '0.0000', 'flat', '1', 'base', '', 'base', '0', '0', '0', '2021-11-05 16:59:17', null, null, null, null, null, '1');
INSERT INTO `config_fee_item` VALUES ('1468424773234180097', 'temp', '12345678912345678912345678912345678912345678912345', '0.0000', '', '1', '', '', 'base', '0', '0', '0', null, null, null, null, '1', '2020-12-03 07:54:58', '0');
INSERT INTO `config_fee_item` VALUES ('6d81c6bc842e4d8ebfdfbd653b5e1f64', 'house', '物业管理费', '66.0000', 'building_area', '12', 'base', '', 'base', '1', '60', '0.6', '2021-11-05 16:19:49', null, '1', '2021-10-21 10:47:24', null, null, '1');
INSERT INTO `config_fee_item` VALUES ('99bcebcea39444bca429a18a413aabdd', 'house', '房屋租金', '1.0000', 'rent_fee', '12', 'base', '', 'base', '0', '0', '0', '2021-11-05 16:06:48', null, null, null, null, null, '1');
INSERT INTO `config_fee_item` VALUES ('c20e0daa4d454ae6977099d6559a813a', 'deposit', '履约保证金', '0.0000', '', '1', '', '', 'base', '0', '0', '0', '2021-11-05 16:07:45', null, null, null, null, null, '1');
INSERT INTO `config_fee_item` VALUES ('e1b3d47f5aa7489fb29b88bb27bd2cf7', 'temp', '场地占用费', '0.0000', '', '1', '', '', 'base', '0', '0', '0', '2021-11-05 16:14:05', null, null, null, null, null, '1');
INSERT INTO `config_fee_item` VALUES ('f638c6f390c0416da94ac961e296997b', 'house', '公共区域电费', '0.0000', 'flat', '1', 'base', '', 'base', '0', '0', '0', '2021-11-05 16:31:16', null, null, null, null, null, '1');

-- ----------------------------
-- Table structure for `config_fee_setting`
-- ----------------------------
DROP TABLE IF EXISTS `config_fee_setting`;
CREATE TABLE `config_fee_setting` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `fee_item_id` varchar(50) NOT NULL COMMENT '收费项分类',
  `type` varchar(50) NOT NULL COMMENT '收费项目名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收费项';

-- ----------------------------
-- Records of config_fee_setting
-- ----------------------------
INSERT INTO `config_fee_setting` VALUES ('1334369266236915714', '1334284783299108865', 'rented');
INSERT INTO `config_fee_setting` VALUES ('1334369266257887234', '6d81c6bc842e4d8ebfdfbd653b5e1f64', 'rented');
INSERT INTO `config_fee_setting` VALUES ('1334369266266275842', '99bcebcea39444bca429a18a413aabdd', 'rented');
INSERT INTO `config_fee_setting` VALUES ('1334371589780668417', '6d81c6bc842e4d8ebfdfbd653b5e1f64', 'selled');
INSERT INTO `config_fee_setting` VALUES ('1334371589789057026', '1334284783299108865', 'selled');

-- ----------------------------
-- Table structure for `config_house`
-- ----------------------------
DROP TABLE IF EXISTS `config_house`;
CREATE TABLE `config_house` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `code` varchar(50) NOT NULL COMMENT '商铺编号',
  `name` varchar(100) DEFAULT NULL COMMENT '商铺名',
  `block` varchar(50) NOT NULL COMMENT '区域',
  `floor` int(3) NOT NULL COMMENT '楼层',
  `state` varchar(50) NOT NULL COMMENT '使用状态',
  `building_square` decimal(10,2) NOT NULL COMMENT '占地面积',
  `use_square` decimal(10,2) NOT NULL COMMENT '使用面积',
  `rent_fee` decimal(10,2) DEFAULT NULL COMMENT '租金',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `creator_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_modify_user_id` varchar(50) DEFAULT NULL COMMENT '修改用户',
  `delete_user_id` varchar(50) DEFAULT NULL COMMENT '删除用户',
  `last_modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `creator_user_id` varchar(50) DEFAULT NULL COMMENT '创建用户',
  `enabled_mark` int(11) NOT NULL COMMENT '有效标志',
  PRIMARY KEY (`id`),
  UNIQUE KEY `house_unique_index` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商铺表';

-- ----------------------------
-- Records of config_house
-- ----------------------------
INSERT INTO `config_house` VALUES ('1466607490492850177', '001', '01-001', '01', '1', 'selled', '200.00', '160.00', '120000.00', null, '2021-12-03 11:17:28', null, null, null, null, '1', '1');
INSERT INTO `config_house` VALUES ('1466607490526404610', '002', '01-002', '01', '1', 'rented', '230.00', '180.00', '150000.00', null, '2021-12-03 11:17:28', null, null, null, null, '1', '1');
INSERT INTO `config_house` VALUES ('1466607490547376129', '003', '01-003', '01', '1', 'selled', '260.00', '200.00', '180000.00', null, '2021-12-03 11:17:28', null, null, null, null, '1', '1');
INSERT INTO `config_house` VALUES ('1466607490555764737', '004', '01-004', '01', '1', 'rented', '290.00', '220.00', '210000.00', null, '2021-12-03 11:17:28', null, null, null, null, '1', '1');
INSERT INTO `config_house` VALUES ('1466607490597707778', '005', '01-005', '01', '1', 'selled', '320.00', '240.00', '240000.00', null, '2021-12-03 11:17:28', null, null, null, null, '1', '1');
INSERT INTO `config_house` VALUES ('1466607490606096385', '006', '01-006', '01', '1', 'selled', '350.00', '260.00', '270000.00', null, '2021-12-03 11:17:28', null, null, null, null, '1', '1');
INSERT INTO `config_house` VALUES ('1466607490622873602', '007', '01-007', '01', '1', 'rented', '380.00', '280.00', '300000.00', null, '2021-12-03 11:17:28', null, null, null, null, '1', '1');
INSERT INTO `config_house` VALUES ('1466607490631262210', '008', '01-008', '01', '1', 'selled', '410.00', '300.00', '330000.00', null, '2021-12-03 11:17:28', null, null, null, null, '1', '1');
INSERT INTO `config_house` VALUES ('1466607490648039426', '009', '01-009', '01', '1', 'selled', '440.00', '320.00', '360000.00', null, '2021-12-03 11:17:28', null, null, null, null, '1', '1');
INSERT INTO `config_house` VALUES ('1466607490656428033', '010', '01-010', '01', '1', 'selled', '470.00', '340.00', '390000.00', null, '2021-12-03 11:17:28', null, null, null, null, '1', '1');
INSERT INTO `config_house` VALUES ('1466607490656428034', '011', '01-011', '01', '1', 'empty', '500.00', '360.00', '420000.00', null, '2021-12-03 11:17:28', null, null, null, null, '1', '1');
INSERT INTO `config_house` VALUES ('1466607490664816641', '012', '01-012', '01', '1', 'empty', '530.00', '380.00', '450000.00', null, '2021-12-03 11:17:28', null, null, null, null, '1', '1');
INSERT INTO `config_house` VALUES ('1466607490673205249', '013', '01-013', '01', '1', 'empty', '560.00', '400.00', '480000.00', null, '2021-12-03 11:17:28', null, null, null, null, '1', '1');
INSERT INTO `config_house` VALUES ('1466607490681593857', '014', '01-014', '01', '1', 'empty', '590.00', '420.00', '510000.00', null, '2021-12-03 11:17:28', null, null, null, null, '1', '1');
INSERT INTO `config_house` VALUES ('1466607490689982465', '015', '01-015', '01', '1', 'empty', '620.00', '440.00', '540000.00', null, '2021-12-03 11:17:28', null, null, null, null, '1', '1');
INSERT INTO `config_house` VALUES ('1466607490698371073', '016', '01-016', '01', '1', 'empty', '650.00', '460.00', '570000.00', null, '2021-12-03 11:17:28', null, null, null, null, '1', '1');
INSERT INTO `config_house` VALUES ('1466607490715148289', '017', '01-017', '01', '1', 'empty', '680.00', '480.00', '600000.00', null, '2021-12-03 11:17:28', null, null, null, null, '1', '1');
INSERT INTO `config_house` VALUES ('1466607490731925506', '018', '01-018', '01', '1', 'empty', '710.00', '500.00', '630000.00', null, '2021-12-03 11:17:28', null, null, null, null, '1', '1');
INSERT INTO `config_house` VALUES ('1466607490740314114', '019', '01-019', '01', '1', 'empty', '740.00', '520.00', '660000.00', null, '2021-12-03 11:17:28', null, null, null, null, '1', '1');
INSERT INTO `config_house` VALUES ('1466607490748702722', '020', '01-020', '01', '1', 'empty', '770.00', '540.00', '690000.00', null, '2021-12-03 11:17:29', null, null, null, null, '1', '1');
INSERT INTO `config_house` VALUES ('1466607490757091330', '021', '01-021', '01', '1', 'empty', '800.00', '560.00', '720000.00', null, '2021-12-03 11:17:29', null, null, null, null, '1', '1');
INSERT INTO `config_house` VALUES ('1466607490765479937', '022', '01-022', '01', '1', 'empty', '830.00', '580.00', '750000.00', null, '2021-12-03 11:17:29', null, null, null, null, '1', '1');
INSERT INTO `config_house` VALUES ('1466607490773868546', '023', '01-023', '01', '1', 'empty', '860.00', '600.00', '780000.00', null, '2021-12-03 11:17:29', null, null, null, null, '1', '1');
INSERT INTO `config_house` VALUES ('1466607490782257153', '024', '01-024', '01', '1', 'empty', '890.00', '620.00', '810000.00', null, '2021-12-03 11:17:29', null, null, null, null, '1', '1');
INSERT INTO `config_house` VALUES ('1466607490790645762', '025', '01-025', '01', '1', 'empty', '920.00', '640.00', '840000.00', null, '2021-12-03 11:17:29', null, null, null, null, '1', '1');
INSERT INTO `config_house` VALUES ('1466607490799034370', '026', '01-026', '01', '1', 'empty', '950.00', '660.00', '870000.00', null, '2021-12-03 11:17:29', null, null, null, null, '1', '1');
INSERT INTO `config_house` VALUES ('1466607490807422978', '027', '01-027', '01', '1', 'empty', '980.00', '680.00', '900000.00', null, '2021-12-03 11:17:29', null, null, null, null, '1', '1');
INSERT INTO `config_house` VALUES ('1466607490815811585', '028', '01-028', '01', '1', 'empty', '1010.00', '700.00', '930000.00', null, '2021-12-03 11:17:29', null, null, null, null, '1', '1');
INSERT INTO `config_house` VALUES ('1466607490824200193', '029', '01-029', '01', '1', 'empty', '1040.00', '720.00', '960000.00', null, '2021-12-03 11:17:29', null, null, null, null, '1', '1');
INSERT INTO `config_house` VALUES ('1466607490840977410', '030', '01-030', '01', '1', 'empty', '1070.00', '740.00', '990000.00', null, '2021-12-03 11:17:29', null, null, null, null, '1', '1');

-- ----------------------------
-- Table structure for `config_house_block`
-- ----------------------------
DROP TABLE IF EXISTS `config_house_block`;
CREATE TABLE `config_house_block` (
  `F_Id` varchar(50) NOT NULL COMMENT '主键',
  `code` varchar(50) NOT NULL COMMENT '商圈编号',
  `name` varchar(100) DEFAULT NULL COMMENT '商圈名',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `creator_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator_user_id` varchar(50) DEFAULT NULL COMMENT '创建用户',
  `last_modify_user_id` varchar(50) DEFAULT NULL COMMENT '修改用户',
  `last_modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `delete_user_id` varchar(50) DEFAULT NULL COMMENT '删除用户',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `enabled_mark` int(11) NOT NULL COMMENT '有效标志',
  PRIMARY KEY (`F_Id`),
  UNIQUE KEY `house_unique_index` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商圈表';

-- ----------------------------
-- Records of config_house_block
-- ----------------------------
INSERT INTO `config_house_block` VALUES ('1468424998715768834', '012', '瀚海大道商业街', null, '2021-12-08 11:39:36', '1', '1', '2021-12-08 17:30:53', null, null, '1');
INSERT INTO `config_house_block` VALUES ('f76112311fba4961aa4326f3be0005bc', '01', '中心区商业街', null, '2021-09-15 18:15:58', 'admin', '1', '2021-12-08 17:30:31', null, null, '1');

-- ----------------------------
-- Table structure for `gen_table`
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table` (
  `table_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(200) DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) DEFAULT '' COMMENT '表描述',
  `sub_table_name` varchar(64) DEFAULT NULL COMMENT '关联子表的表名',
  `sub_table_fk_name` varchar(64) DEFAULT NULL COMMENT '子表关联的外键名',
  `class_name` varchar(100) DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `package_name` varchar(100) DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(30) DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(50) DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(50) DEFAULT NULL COMMENT '生成功能作者',
  `gen_type` char(1) DEFAULT '0' COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
  `gen_path` varchar(200) DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
  `options` varchar(1000) DEFAULT NULL COMMENT '其它生成选项',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`table_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代码生成业务表';

-- ----------------------------
-- Records of gen_table
-- ----------------------------

-- ----------------------------
-- Table structure for `gen_table_column`
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_column`;
CREATE TABLE `gen_table_column` (
  `column_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` varchar(64) DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` char(1) DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` char(1) DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` char(1) DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` char(1) DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` char(1) DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) DEFAULT '' COMMENT '字典类型',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`column_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代码生成业务表字段';

-- ----------------------------
-- Records of gen_table_column
-- ----------------------------

-- ----------------------------
-- Table structure for `payment_bill`
-- ----------------------------
DROP TABLE IF EXISTS `payment_bill`;
CREATE TABLE `payment_bill` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `type` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '账单分类',
  `resource_name` varchar(50) NOT NULL COMMENT '资源名',
  `resource_id` varchar(100) NOT NULL COMMENT '资源id',
  `contract_id` varchar(50) NOT NULL COMMENT '合同',
  `fee_item_id` varchar(50) NOT NULL COMMENT '收费项id',
  `fee_item_name` varchar(50) NOT NULL COMMENT '收费项名',
  `fee_user` varchar(255) NOT NULL COMMENT '客户姓名',
  `begin_date` date NOT NULL COMMENT '账单对应的周期',
  `end_date` date NOT NULL COMMENT '账单对应的周期',
  `deadline` date NOT NULL,
  `last_index` decimal(11,2) DEFAULT NULL COMMENT '起数',
  `current_index` decimal(11,2) DEFAULT NULL COMMENT '止数',
  `multiple` decimal(4,2) NOT NULL COMMENT '倍率',
  `loss` decimal(11,2) NOT NULL COMMENT '损耗',
  `num` decimal(11,2) NOT NULL COMMENT '数量',
  `price` decimal(11,4) NOT NULL COMMENT '单价',
  `total` decimal(11,2) NOT NULL COMMENT '总价',
  `late_fee` decimal(11,2) DEFAULT NULL COMMENT '滞纳金',
  `discount` decimal(11,2) DEFAULT NULL COMMENT '折扣',
  `receivable` decimal(11,2) DEFAULT NULL COMMENT '应收',
  `pay_state` char(1) NOT NULL DEFAULT '0',
  `pay_log_no` varchar(50) NOT NULL DEFAULT '' COMMENT '流水号',
  `pay_log_id` varchar(50) NOT NULL DEFAULT '' COMMENT '流水表id',
  `pay_time` datetime DEFAULT NULL,
  `creator_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator_user_id` varchar(50) DEFAULT NULL COMMENT '创建用户',
  `last_modify_user_id` varchar(50) DEFAULT NULL COMMENT '修改用户',
  `last_modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `delete_user_id` varchar(50) DEFAULT NULL COMMENT '删除用户',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `enabled_mark` int(11) NOT NULL COMMENT '有效标志',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账单表';

-- ----------------------------
-- Records of payment_bill
-- ----------------------------
INSERT INTO `payment_bill` VALUES ('1416326361362014209', 'house', '01-001', '1466607490492850177', '1411227278918098946', '6d81c6bc842e4d8ebfdfbd653b5e1f64', '物业管理费', '张一', '2021-07-03', '2022-07-02', '2021-08-16', '0.00', '0.00', '1.00', '0.00', '200.00', '66.0000', '13200.00', '0.00', '0.00', '13200.00', '1', 'PAY2021071700201', '1416368224999645185', '2021-07-17 20:04:34', '2021-07-17 17:18:13', '1', '1', '2021-07-17 20:04:34', null, null, '1');
INSERT INTO `payment_bill` VALUES ('1416326361370402818', 'house', '01-002', '1466607490526404610', '1411227695068553218', '6d81c6bc842e4d8ebfdfbd653b5e1f64', '物业管理费', '张二', '2021-07-03', '2022-07-02', '2021-08-16', '0.00', '0.00', '1.00', '0.00', '230.00', '66.0000', '15180.00', '0.00', '0.00', '15180.00', '1', 'PAY2021091800204', '1439069972805459969', '2021-09-18 11:33:13', '2021-07-17 17:18:13', '1', '1', '2021-09-18 11:33:13', null, null, '1');
INSERT INTO `payment_bill` VALUES ('1416326361370402819', 'house', '01-005', '1466607490597707778', '1411231996415385601', '6d81c6bc842e4d8ebfdfbd653b5e1f64', '物业管理费', '张武', '2021-07-03', '2022-07-02', '2021-08-16', '0.00', '0.00', '1.00', '0.00', '320.00', '66.0000', '21120.00', '0.00', '0.00', '21120.00', '1', 'PAY2021081800206', '1427833133427859457', '2021-08-18 11:22:02', '2021-07-17 17:18:13', '1', '1', '2021-08-18 11:22:02', null, null, '1');
INSERT INTO `payment_bill` VALUES ('1416326361378791425', 'house', '01-006', '1466607490606096385', '1411232605394771969', '6d81c6bc842e4d8ebfdfbd653b5e1f64', '物业管理费', '张六', '2021-07-03', '2022-07-02', '2021-08-16', '0.00', '0.00', '1.00', '0.00', '350.00', '66.0000', '23100.00', '0.00', '0.00', null, '0', '', '', null, '2021-07-17 17:18:13', '1', null, null, null, null, '1');
INSERT INTO `payment_bill` VALUES ('1416326361378791426', 'house', '01-008', '1466607490631262210', '1411233525880922113', '6d81c6bc842e4d8ebfdfbd653b5e1f64', '物业管理费', '丈八', '2021-07-06', '2022-07-05', '2021-08-16', '0.00', '0.00', '1.00', '0.00', '410.00', '66.0000', '27060.00', '0.00', '0.00', null, '0', '', '', null, '2021-07-17 17:18:13', '1', null, null, null, null, '1');
INSERT INTO `payment_bill` VALUES ('1416326361391374338', 'house', '01-009', '1466607490648039426', '1411234413177544705', '6d81c6bc842e4d8ebfdfbd653b5e1f64', '物业管理费', '张久', '2021-07-06', '2022-07-05', '2021-08-16', '0.00', '0.00', '1.00', '0.00', '440.00', '66.0000', '29040.00', '0.00', '0.00', null, '0', '', '', null, '2021-07-17 17:18:13', '1', null, null, null, null, '1');
INSERT INTO `payment_bill` VALUES ('1416326361391374339', 'house', '01-007', '1466607490622873602', '1411233080567472130', '6d81c6bc842e4d8ebfdfbd653b5e1f64', '物业管理费', '张琪', '2021-07-14', '2022-07-13', '2021-08-16', '0.00', '0.00', '1.00', '0.00', '380.00', '66.0000', '25080.00', '0.00', '0.00', null, '0', '', '', null, '2021-07-17 17:18:13', '1', null, null, null, null, '1');
INSERT INTO `payment_bill` VALUES ('1416326361399762945', 'house', '01-010', '1466607490656428033', '1411234730111737858', '6d81c6bc842e4d8ebfdfbd653b5e1f64', '物业管理费', '张氏', '2021-07-16', '2022-07-15', '2021-08-16', '0.00', '0.00', '1.00', '0.00', '470.00', '66.0000', '31020.00', '0.00', '0.00', null, '0', '', '', null, '2021-07-17 17:18:13', '1', null, null, null, null, '1');
INSERT INTO `payment_bill` VALUES ('1416326361408151554', 'house', '01-003', '1466607490547376129', '1411228480259039234', '6d81c6bc842e4d8ebfdfbd653b5e1f64', '物业管理费', '张三', '2021-07-18', '2022-07-17', '2021-08-16', '0.00', '0.00', '1.00', '0.00', '260.00', '66.0000', '17160.00', '0.00', '0.00', '17160.00', '1', 'PAY2021081800204', '1427832441472557057', '2021-08-18 11:19:17', '2021-07-17 17:18:13', '1', '1', '2021-08-18 11:19:17', null, null, '1');
INSERT INTO `payment_bill` VALUES ('1416331156458119169', 'house', '01-001', '1466607490492850177', '1411227278918098946', '1334284783299108865', '抄表水费', '张一', '2021-07-03', '2021-08-01', '2021-08-31', '0.00', '100.00', '1.00', '0.00', '100.00', '0.4500', '45.00', '0.00', '0.00', '45.00', '1', 'PAY2021071700201', '1416368224999645185', '2021-07-17 20:04:34', '2021-07-17 17:37:17', '1', '1', '2021-07-17 20:04:34', null, null, '1');
INSERT INTO `payment_bill` VALUES ('1416331156466507778', 'house', '01-002', '1466607490526404610', '1411227695068553218', '1334284783299108865', '抄表水费', '张二', '2021-07-03', '2021-08-01', '2021-08-31', '0.00', '150.00', '1.00', '0.00', '150.00', '0.4500', '67.50', '0.00', '0.00', '67.50', '1', 'PAY2021091800204', '1439069972805459969', '2021-09-18 11:33:13', '2021-07-17 17:37:17', '1', '1', '2021-09-18 11:33:13', null, null, '1');
INSERT INTO `payment_bill` VALUES ('1416331156466507779', 'house', '01-003', '1466607490547376129', '1411228480259039234', '1334284783299108865', '抄表水费', '张三', '2021-07-18', '2021-08-01', '2021-08-31', '0.00', '30.00', '1.00', '0.00', '30.00', '0.4500', '13.50', '0.00', '0.00', '13.50', '1', 'PAY2021081800204', '1427832441472557057', '2021-08-18 11:19:17', '2021-07-17 17:37:17', '1', '1', '2021-08-18 11:19:17', null, null, '1');
INSERT INTO `payment_bill` VALUES ('1416331156474896385', 'house', '01-005', '1466607490597707778', '1411231996415385601', '1334284783299108865', '抄表水费', '张武', '2021-07-03', '2021-08-01', '2021-08-31', '0.00', '35.00', '1.00', '0.00', '35.00', '0.4500', '15.75', '0.00', '0.00', '15.75', '1', 'PAY2021081800206', '1427833133427859457', '2021-08-18 11:22:02', '2021-07-17 17:37:17', '1', '1', '2021-08-18 11:22:02', null, null, '1');
INSERT INTO `payment_bill` VALUES ('1416331156474896386', 'house', '01-006', '1466607490606096385', '1411232605394771969', '1334284783299108865', '抄表水费', '张六', '2021-07-03', '2021-08-01', '2021-08-31', '0.00', '150.00', '1.00', '0.00', '150.00', '0.4500', '67.50', '0.00', '0.00', null, '0', '', '', null, '2021-07-17 17:37:17', '1', null, null, null, null, '1');
INSERT INTO `payment_bill` VALUES ('1416331156474896387', 'house', '01-007', '1466607490622873602', '1411233080567472130', '1334284783299108865', '抄表水费', '张琪', '2021-07-14', '2021-08-01', '2021-08-31', '0.00', '1.00', '1.00', '0.00', '1.00', '0.4500', '0.45', '0.00', '0.00', null, '0', '', '', null, '2021-07-17 17:37:17', '1', null, null, null, null, '1');
INSERT INTO `payment_bill` VALUES ('1416331156483284994', 'house', '01-008', '1466607490631262210', '1411233525880922113', '1334284783299108865', '抄表水费', '丈八', '2021-07-06', '2021-08-01', '2021-08-31', '0.00', '30.00', '1.00', '0.00', '30.00', '0.4500', '13.50', '0.00', '0.00', null, '0', '', '', null, '2021-07-17 17:37:17', '1', null, null, null, null, '1');
INSERT INTO `payment_bill` VALUES ('1416331156483284995', 'house', '01-009', '1466607490648039426', '1411234413177544705', '1334284783299108865', '抄表水费', '张久', '2021-07-06', '2021-08-01', '2021-08-31', '0.00', '30.00', '1.00', '0.00', '30.00', '0.4500', '13.50', '0.00', '0.00', null, '0', '', '', null, '2021-07-17 17:37:17', '1', null, null, null, null, '1');
INSERT INTO `payment_bill` VALUES ('1416331156483284996', 'house', '01-010', '1466607490656428033', '1411234730111737858', '1334284783299108865', '抄表水费', '张氏', '2021-07-16', '2021-08-01', '2021-08-31', '0.00', '30.00', '1.00', '0.00', '30.00', '0.4500', '13.50', '0.00', '0.00', null, '0', '', '', null, '2021-07-17 17:37:17', '1', null, null, null, null, '1');
INSERT INTO `payment_bill` VALUES ('1416331589234794498', 'house', '01-004', '1466607490555764737', '1411230553029550082', '6d81c6bc842e4d8ebfdfbd653b5e1f64', '物业管理费', '张四', '2021-06-22', '2022-06-21', '2021-08-17', '0.00', '0.00', '1.00', '0.00', '290.00', '66.0000', '19140.00', '0.00', '10000.00', '9140.00', '1', 'PAY2021081800205', '1427832903064100866', '2021-08-18 11:21:07', '2021-07-17 17:39:00', '1', '1', '2021-08-18 11:21:07', null, null, '1');
INSERT INTO `payment_bill` VALUES ('1416334276873494529', 'house', '01-004', '1466607490555764737', '1411230553029550082', '1334284783299108865', '抄表水费', '张四', '2021-06-22', '2021-07-08', '2021-08-25', '0.00', '1.00', '1.10', '0.00', '1.10', '0.4500', '0.50', '0.00', '0.00', '0.50', '1', 'PAY2021081800205', '1427832903064100866', '2021-08-18 11:21:07', '2021-07-17 17:49:40', '1', '1', '2021-08-18 11:21:07', null, null, '1');
INSERT INTO `payment_bill` VALUES ('1416334746941726722', 'house', '01-004', '1466607490555764737', '1411230553029550082', '99bcebcea39444bca429a18a413aabdd', '房屋租金', '张四', '2021-06-22', '2022-06-21', '2021-08-12', '0.00', '0.00', '1.00', '0.00', '210000.00', '1.0000', '210000.00', '0.00', '0.00', '210000.00', '1', 'PAY2021081800205', '1427832903064100866', '2021-08-18 11:21:07', '2021-07-17 17:51:33', '1', '1', '2021-08-18 11:21:07', null, null, '1');
INSERT INTO `payment_bill` VALUES ('1416334746950115329', 'house', '01-002', '1466607490526404610', '1411227695068553218', '99bcebcea39444bca429a18a413aabdd', '房屋租金', '张二', '2021-07-03', '2022-07-02', '2021-08-12', '0.00', '0.00', '1.00', '0.00', '150000.00', '1.0000', '150000.00', '0.00', '0.00', '150000.00', '1', 'PAY2021091800204', '1439069972805459969', '2021-09-18 11:33:13', '2021-07-17 17:51:33', '1', '1', '2021-09-18 11:33:13', null, null, '1');
INSERT INTO `payment_bill` VALUES ('1416334746950115330', 'house', '01-007', '1466607490622873602', '1411233080567472130', '99bcebcea39444bca429a18a413aabdd', '房屋租金', '张琪', '2021-07-14', '2022-07-13', '2021-08-12', '0.00', '0.00', '1.00', '0.00', '300000.00', '1.0000', '300000.00', '0.00', '0.00', null, '0', '', '', null, '2021-07-17 17:51:33', '1', null, null, null, null, '1');
INSERT INTO `payment_bill` VALUES ('1427808297376817154', 'house', '01-001', '1466607490492850177', '1411227278918098946', '1334284783299108865', '抄表水费', '张一', '2021-08-02', '2021-08-31', '2021-09-22', '100.00', '120.00', '1.00', '0.00', '20.00', '0.4500', '9.00', '0.00', '0.00', '9.00', '1', 'PAY2021081800201', '1427823644477759490', '2021-08-18 10:44:19', '2021-08-18 09:43:20', '1', '1', '2021-08-18 10:44:19', null, null, '1');
INSERT INTO `payment_bill` VALUES ('1427808297385205762', 'house', '01-002', '1466607490526404610', '1411227695068553218', '1334284783299108865', '抄表水费', '张二', '2021-08-02', '2021-08-31', '2021-09-22', '0.00', '110.00', '1.00', '0.00', '110.00', '0.4500', '49.50', '0.00', '0.00', '49.50', '1', 'PAY2021091800204', '1439069972805459969', '2021-09-18 11:33:13', '2021-08-18 09:43:20', '1', '1', '2021-09-18 11:33:13', null, null, '1');
INSERT INTO `payment_bill` VALUES ('1427808297385205763', 'house', '01-003', '1466607490547376129', '1411228480259039234', '1334284783299108865', '抄表水费', '张三', '2021-08-02', '2021-08-31', '2021-09-22', '30.00', '31.00', '1.00', '0.00', '1.00', '0.4500', '0.45', '0.00', '0.00', '0.45', '1', 'PAY2021081800204', '1427832441472557057', '2021-08-18 11:19:17', '2021-08-18 09:43:20', '1', '1', '2021-08-18 11:19:17', null, null, '1');
INSERT INTO `payment_bill` VALUES ('1427808297393594370', 'house', '01-007', '1466607490622873602', '1411233080567472130', '1334284783299108865', '抄表水费', '张琪', '2021-08-02', '2021-08-31', '2021-09-22', '1.00', '10.00', '1.00', '0.00', '9.00', '0.4500', '4.05', '0.00', '0.00', null, '0', '', '', null, '2021-08-18 09:43:20', '1', null, null, null, null, '1');
INSERT INTO `payment_bill` VALUES ('1427808297393594371', 'house', '01-008', '1466607490631262210', '1411233525880922113', '1334284783299108865', '抄表水费', '丈八', '2021-08-02', '2021-08-31', '2021-09-22', '30.00', '31.00', '1.00', '0.00', '1.00', '0.4500', '0.45', '0.00', '0.00', null, '0', '', '', null, '2021-08-18 09:43:20', '1', null, null, null, null, '1');
INSERT INTO `payment_bill` VALUES ('1427808297414565889', 'house', '01-009', '1466607490648039426', '1411234413177544705', '1334284783299108865', '抄表水费', '张久', '2021-08-02', '2021-08-31', '2021-09-22', '30.00', '50.00', '1.00', '0.00', '20.00', '0.4500', '9.00', '0.00', '0.00', null, '0', '', '', null, '2021-08-18 09:43:20', '1', null, null, null, null, '1');
INSERT INTO `payment_bill` VALUES ('1427808297414565890', 'house', '01-010', '1466607490656428033', '1411234730111737858', '1334284783299108865', '抄表水费', '张氏', '2021-08-02', '2021-08-31', '2021-09-22', '30.00', '31.00', '1.00', '0.00', '1.00', '0.4500', '0.45', '0.00', '0.00', null, '0', '', '', null, '2021-08-18 09:43:20', '1', null, null, null, null, '1');
INSERT INTO `payment_bill` VALUES ('1439069430603587585', 'house', '01-001', '1466607490492850177', '1411227278918098946', '1334284783299108865', '抄表水费', '张一', '2021-09-01', '2021-09-30', '2021-09-30', '120.00', '200.00', '1.00', '0.00', '80.00', '0.4500', '36.00', '0.00', '0.00', '36.00', '1', 'PAY2021091800201', '1439069562287955970', '2021-09-18 11:31:35', '2021-09-18 11:31:04', '1', '1', '2021-09-18 11:31:35', null, null, '1');
INSERT INTO `payment_bill` VALUES ('1439069430611976194', 'house', '01-002', '1466607490526404610', '1411227695068553218', '1334284783299108865', '抄表水费', '张二', '2021-09-01', '2021-09-30', '2021-09-30', '110.00', '320.00', '1.00', '0.00', '210.00', '0.4500', '94.50', '0.00', '0.00', '94.50', '1', 'PAY2021091800204', '1439069972805459969', '2021-09-18 11:33:13', '2021-09-18 11:31:04', '1', '1', '2021-09-18 11:33:13', null, null, '1');
INSERT INTO `payment_bill` VALUES ('1439069430611976195', 'house', '01-003', '1466607490547376129', '1411228480259039234', '1334284783299108865', '抄表水费', '张三', '2021-09-01', '2021-09-30', '2021-09-30', '31.00', '160.00', '1.00', '0.00', '129.00', '0.4500', '58.05', '0.00', '0.00', null, '0', '', '', null, '2021-09-18 11:31:04', '1', null, null, null, null, '1');
INSERT INTO `payment_bill` VALUES ('1439069430628753409', 'house', '01-007', '1466607490622873602', '1411233080567472130', '1334284783299108865', '抄表水费', '张琪', '2021-09-01', '2021-09-30', '2021-09-30', '10.00', '150.00', '1.00', '0.00', '140.00', '0.4500', '63.00', '0.00', '0.00', null, '0', '', '', null, '2021-09-18 11:31:04', '1', null, null, null, null, '1');
INSERT INTO `payment_bill` VALUES ('1439069430628753410', 'house', '01-008', '1466607490631262210', '1411233525880922113', '1334284783299108865', '抄表水费', '丈八', '2021-09-01', '2021-09-30', '2021-09-30', '31.00', '320.00', '1.00', '0.00', '289.00', '0.4500', '130.05', '0.00', '0.00', null, '0', '', '', null, '2021-09-18 11:31:04', '1', null, null, null, null, '1');
INSERT INTO `payment_bill` VALUES ('1439069430637142017', 'house', '01-009', '1466607490648039426', '1411234413177544705', '1334284783299108865', '抄表水费', '张久', '2021-09-01', '2021-09-30', '2021-09-30', '50.00', '270.00', '1.00', '0.00', '220.00', '0.4500', '99.00', '0.00', '0.00', null, '0', '', '', null, '2021-09-18 11:31:04', '1', null, null, null, null, '1');
INSERT INTO `payment_bill` VALUES ('1439069430637142018', 'house', '01-010', '1466607490656428033', '1411234730111737858', '1334284783299108865', '抄表水费', '张氏', '2021-09-01', '2021-09-30', '2021-09-30', '31.00', '200.00', '1.00', '0.00', '169.00', '0.4500', '76.05', '0.00', '0.00', null, '0', '', '', null, '2021-09-18 11:31:04', '1', null, null, null, null, '1');
INSERT INTO `payment_bill` VALUES ('1451074219520581633', 'house', '01-004', '1466607490555764737', '1411230553029550082', '1334284783299108865', '抄表水费', '张四', '2021-07-09', '2021-08-08', '2021-11-30', '1.00', '2.00', '1.00', '0.00', '1.00', '0.4500', '0.45', '0.00', '0.00', null, '0', '', '', null, '2021-10-21 14:33:48', '1', null, null, null, null, '1');
INSERT INTO `payment_bill` VALUES ('1451074219558330369', 'house', '01-005', '1466607490597707778', '1411231996415385601', '1334284783299108865', '抄表水费', '张武', '2021-08-02', '2021-09-01', '2021-11-30', '35.00', '40.00', '1.00', '0.00', '5.00', '0.4500', '2.25', '0.00', '0.00', null, '0', '', '', null, '2021-10-21 14:33:48', '1', null, null, null, null, '1');
INSERT INTO `payment_bill` VALUES ('1451074219566718978', 'house', '01-006', '1466607490606096385', '1411232605394771969', '1334284783299108865', '抄表水费', '张六', '2021-08-02', '2021-09-01', '2021-11-30', '150.00', '200.00', '1.00', '0.00', '50.00', '0.4500', '22.50', '0.00', '0.00', null, '0', '', '', null, '2021-10-21 14:33:48', '1', null, null, null, null, '1');
INSERT INTO `payment_bill` VALUES ('1451074219575107586', 'house', '01-001', '1466607490492850177', '1411227278918098946', '1334284783299108865', '抄表水费', '张一', '2021-10-01', '2021-10-31', '2021-11-30', '200.00', '300.00', '1.00', '0.00', '100.00', '0.4500', '45.00', '0.00', '0.00', null, '0', '', '', null, '2021-10-21 14:33:48', '1', null, null, null, null, '1');
INSERT INTO `payment_bill` VALUES ('1451074219583496194', 'house', '01-002', '1466607490526404610', '1411227695068553218', '1334284783299108865', '抄表水费', '张二', '2021-10-01', '2021-10-31', '2021-11-30', '320.00', '330.00', '1.00', '0.00', '10.00', '0.4500', '4.50', '0.00', '0.00', null, '0', '', '', null, '2021-10-21 14:33:48', '1', null, null, null, null, '1');
INSERT INTO `payment_bill` VALUES ('1451074219600273409', 'house', '01-003', '1466607490547376129', '1411228480259039234', '1334284783299108865', '抄表水费', '张三', '2021-10-01', '2021-10-31', '2021-11-30', '160.00', '170.00', '1.00', '0.00', '10.00', '0.4500', '4.50', '0.00', '0.00', null, '0', '', '', null, '2021-10-21 14:33:48', '1', null, null, null, null, '1');
INSERT INTO `payment_bill` VALUES ('1451074219612856322', 'house', '01-007', '1466607490622873602', '1411233080567472130', '1334284783299108865', '抄表水费', '张琪', '2021-10-01', '2021-10-31', '2021-11-30', '150.00', '200.00', '1.00', '0.00', '50.00', '0.4500', '22.50', '0.00', '0.00', null, '0', '', '', null, '2021-10-21 14:33:48', '1', null, null, null, null, '1');

-- ----------------------------
-- Table structure for `payment_contract`
-- ----------------------------
DROP TABLE IF EXISTS `payment_contract`;
CREATE TABLE `payment_contract` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `block_code` varchar(50) NOT NULL COMMENT '区域编码',
  `resource_id` varchar(50) NOT NULL COMMENT '资源id',
  `resource_name` varchar(50) NOT NULL COMMENT '资源名',
  `resource_type` varchar(50) NOT NULL COMMENT '资源类型',
  `contract_type` varchar(50) NOT NULL COMMENT '合同类型',
  `begin_date` datetime NOT NULL COMMENT '开始使用时间',
  `period` varchar(20) DEFAULT NULL COMMENT '合约有效时间',
  `end_date` datetime DEFAULT NULL COMMENT '结束使用时间',
  `user_name` varchar(50) NOT NULL COMMENT '租户姓名/业主姓名',
  `user_idcard` varchar(50) NOT NULL COMMENT '租户身份证/业主身份证',
  `user_phone` varchar(50) NOT NULL COMMENT '联系方式',
  `user_gender` varchar(50) NOT NULL COMMENT '性别',
  `user_trade` varchar(50) DEFAULT NULL COMMENT '从事的行业',
  `user_trade_detail` varchar(200) DEFAULT NULL COMMENT '行业的详细描述',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `enabled_mark` int(11) NOT NULL COMMENT '有效标志',
  `creator_user_id` varchar(50) DEFAULT NULL COMMENT '创建用户',
  `creator_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_modify_user_id` varchar(50) DEFAULT NULL COMMENT '修改用户',
  `last_modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `delete_user_id` varchar(50) DEFAULT NULL COMMENT '删除用户',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='出租出售合同';

-- ----------------------------
-- Records of payment_contract
-- ----------------------------
INSERT INTO `payment_contract` VALUES ('1411227278918098946', '01', '1466607490492850177', '01-001', 'house', 'selled', '2021-07-03 00:00:00', '0', null, '张一', '140702198502036501', '13265412321', '84ff10b322d74ac3a653e3176724f909', '0fcc751d-9ceb-4767-8755-f0189b239468', null, null, '1', '1', '2021-07-03 15:36:17', '1', '2021-07-17 17:22:24', null, null);
INSERT INTO `payment_contract` VALUES ('1411227695068553218', '01', '1466607490526404610', '01-002', 'house', 'rented', '2021-07-03 15:37:48', '36', '2024-07-03 15:37:47', '张二', '140702195603023202', '13561325653', '84ff10b322d74ac3a653e3176724f909', '1b82cfae-98f1-4b54-a3ae-1d1c6ad3f3f6', null, null, '1', '1', '2021-07-03 15:37:57', null, null, null, null);
INSERT INTO `payment_contract` VALUES ('1411228480259039234', '01', '1466607490547376129', '01-003', 'house', 'selled', '2021-07-18 00:00:00', '0', null, '张三', '140702195603032603', '13562356253', '84ff10b322d74ac3a653e3176724f909', '0fcc751d-9ceb-4767-8755-f0189b239468', null, null, '1', '1', '2021-07-03 15:41:04', null, null, null, null);
INSERT INTO `payment_contract` VALUES ('1411230553029550082', '01', '1466607490555764737', '01-004', 'house', 'rented', '2021-06-22 00:00:00', '24', '2023-06-21 23:59:59', '张四', '140236198702023256', '13651325896', '84ff10b322d74ac3a653e3176724f909', '27c38cf2-dc0a-4449-ab87-7eb68f7e425b', null, null, '1', '1', '2021-07-03 15:49:18', null, null, null, null);
INSERT INTO `payment_contract` VALUES ('1411231996415385601', '01', '1466607490597707778', '01-005', 'house', 'selled', '2021-07-03 15:54:57', '0', null, '张武', '140702198702036505', '13684652365', 'd06778318f894c4b914050601897effe', '27c38cf2-dc0a-4449-ab87-7eb68f7e425b', null, null, '1', '1', '2021-07-03 15:55:02', null, null, null, null);
INSERT INTO `payment_contract` VALUES ('1411232605394771969', '01', '1466607490606096385', '01-006', 'house', 'selled', '2021-07-03 15:57:16', '0', null, '张六', '140702198802036506', '13651236589', 'd06778318f894c4b914050601897effe', '27c38cf2-dc0a-4449-ab87-7eb68f7e425b', null, null, '1', '1', '2021-07-03 15:57:27', null, null, null, null);
INSERT INTO `payment_contract` VALUES ('1411233080567472130', '01', '1466607490622873602', '01-007', 'house', 'rented', '2021-07-14 00:00:00', '24', '2023-07-13 23:59:59', '张琪', '140702196602032307', '13562365896', '84ff10b322d74ac3a653e3176724f909', '4d885793-2fbd-49ce-9b0e-aa709a71b2d0', null, null, '1', '1', '2021-07-03 15:59:21', null, null, null, null);
INSERT INTO `payment_contract` VALUES ('1411233525880922113', '01', '1466607490631262210', '01-008', 'house', 'selled', '2021-07-06 00:00:00', '0', null, '丈八', '140702196605062308', '13256589563', 'd06778318f894c4b914050601897effe', '44338471-6e77-4dd0-899d-ea0355c03277', null, null, '1', '1', '2021-07-03 16:01:07', null, null, null, null);
INSERT INTO `payment_contract` VALUES ('1411234413177544705', '01', '1466607490648039426', '01-009', 'house', 'selled', '2021-07-06 00:00:00', '0', null, '张久', '140702198603053209', '13251236985', '84ff10b322d74ac3a653e3176724f909', '27d8cf5a-22e8-4e05-b4d6-5684a564418a', null, null, '1', '1', '2021-07-03 16:04:38', null, null, null, null);
INSERT INTO `payment_contract` VALUES ('1411234730111737858', '01', '1466607490656428033', '01-010', 'house', 'selled', '2021-07-16 00:00:00', '0', null, '张氏', '140702198603035610', '13251325452', 'd06778318f894c4b914050601897effe', '44338471-6e77-4dd0-899d-ea0355c03277', null, null, '1', '1', '2021-07-03 16:05:54', null, null, null, null);
INSERT INTO `payment_contract` VALUES ('1466653775350562817', '01', '1466607490492850177', '01-001', 'house', 'selled', '2021-12-03 00:00:00', '0', null, '张一', '140702198801087001', '13654123658', '84ff10b322d74ac3a653e3176724f909', '0fcc751d-9ceb-4767-8755-f0189b239468', null, null, '0', '1', '2021-12-03 14:21:24', '1', '2021-12-03 15:10:41', null, null);
INSERT INTO `payment_contract` VALUES ('1466654129949605889', '01', '1466607490526404610', '01-002', 'house', 'rented', '2021-12-03 00:00:00', '60', '2026-12-02 23:59:59', '张二', '140702198801087002', '13265985632', '84ff10b322d74ac3a653e3176724f909', '0fcc751d-9ceb-4767-8755-f0189b239468', null, null, '0', '1', '2021-12-03 14:22:48', '1', '2021-12-03 14:27:18', null, null);
INSERT INTO `payment_contract` VALUES ('1466654405758648322', '01', '1466607490547376129', '01-003', 'house', 'selled', '2021-12-18 00:00:00', '60', null, '张三', '140702198801087003', '13652365258', '84ff10b322d74ac3a653e3176724f909', '0fcc751d-9ceb-4767-8755-f0189b239468', null, null, '0', '1', '2021-12-03 14:23:54', '1', '2021-12-03 14:27:13', null, null);

-- ----------------------------
-- Table structure for `payment_contract_fee`
-- ----------------------------
DROP TABLE IF EXISTS `payment_contract_fee`;
CREATE TABLE `payment_contract_fee` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `fee_item_id` varchar(50) NOT NULL COMMENT '收费项id',
  `contract_id` varchar(50) NOT NULL COMMENT '资源id',
  `begin_date` date NOT NULL,
  `end_date` date DEFAULT NULL COMMENT '收费结束时间',
  `next_bill_date` date NOT NULL,
  `enabled_mark` int(11) NOT NULL COMMENT '有效标志',
  `creator_user_id` varchar(50) DEFAULT NULL COMMENT '创建用户',
  `creator_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_modify_user_id` varchar(50) DEFAULT NULL COMMENT '修改用户',
  `last_modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `delete_user_id` varchar(50) DEFAULT NULL COMMENT '删除用户',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='合同收费项目';

-- ----------------------------
-- Records of payment_contract_fee
-- ----------------------------
INSERT INTO `payment_contract_fee` VALUES ('1411227278964236289', '6d81c6bc842e4d8ebfdfbd653b5e1f64', '1411227278918098946', '2021-07-03', null, '2022-07-03', '1', '1', '2021-07-03 15:36:17', '1', '2021-07-17 17:22:24', null, null);
INSERT INTO `payment_contract_fee` VALUES ('1411227278993596418', '1334284783299108865', '1411227278918098946', '2021-07-03', null, '2021-11-01', '1', '1', '2021-07-03 15:36:17', '1', '2021-07-17 17:22:24', null, null);
INSERT INTO `payment_contract_fee` VALUES ('1411227695085330433', '1334284783299108865', '1411227695068553218', '2021-07-03', '2024-07-03', '2021-11-01', '1', '1', '2021-07-03 15:37:57', null, null, null, null);
INSERT INTO `payment_contract_fee` VALUES ('1411227695102107650', '6d81c6bc842e4d8ebfdfbd653b5e1f64', '1411227695068553218', '2021-07-03', '2024-07-03', '2022-07-03', '1', '1', '2021-07-03 15:37:57', null, null, null, null);
INSERT INTO `payment_contract_fee` VALUES ('1411227695118884865', '99bcebcea39444bca429a18a413aabdd', '1411227695068553218', '2021-07-03', '2024-07-03', '2022-06-09', '1', '1', '2021-07-03 15:37:57', null, null, null, null);
INSERT INTO `payment_contract_fee` VALUES ('1411228480292593665', '6d81c6bc842e4d8ebfdfbd653b5e1f64', '1411228480259039234', '2021-07-18', null, '2022-07-03', '1', '1', '2021-07-03 15:41:04', null, null, null, null);
INSERT INTO `payment_contract_fee` VALUES ('1411228480309370881', '1334284783299108865', '1411228480259039234', '2021-07-18', null, '2021-11-01', '1', '1', '2021-07-03 15:41:04', null, null, null, null);
INSERT INTO `payment_contract_fee` VALUES ('1411230553084076033', '1334284783299108865', '1411230553029550082', '2021-06-22', '2023-06-21', '2021-08-09', '1', '1', '2021-07-03 15:49:18', null, null, null, null);
INSERT INTO `payment_contract_fee` VALUES ('1411230553105047553', '6d81c6bc842e4d8ebfdfbd653b5e1f64', '1411230553029550082', '2021-06-22', '2023-06-21', '2022-06-16', '1', '1', '2021-07-03 15:49:18', null, null, null, null);
INSERT INTO `payment_contract_fee` VALUES ('1411230553113436162', '99bcebcea39444bca429a18a413aabdd', '1411230553029550082', '2021-06-22', '2023-06-21', '2022-06-09', '1', '1', '2021-07-03 15:49:18', null, null, null, null);
INSERT INTO `payment_contract_fee` VALUES ('1411231996423774209', '6d81c6bc842e4d8ebfdfbd653b5e1f64', '1411231996415385601', '2021-07-03', null, '2022-07-03', '1', '1', '2021-07-03 15:55:02', null, null, null, null);
INSERT INTO `payment_contract_fee` VALUES ('1411231996432162818', '1334284783299108865', '1411231996415385601', '2021-07-03', null, '2021-09-02', '1', '1', '2021-07-03 15:55:02', null, null, null, null);
INSERT INTO `payment_contract_fee` VALUES ('1411232605411549186', '6d81c6bc842e4d8ebfdfbd653b5e1f64', '1411232605394771969', '2021-07-03', null, '2022-07-03', '1', '1', '2021-07-03 15:57:27', null, null, null, null);
INSERT INTO `payment_contract_fee` VALUES ('1411232605419937793', '1334284783299108865', '1411232605394771969', '2021-07-03', null, '2021-09-02', '1', '1', '2021-07-03 15:57:27', null, null, null, null);
INSERT INTO `payment_contract_fee` VALUES ('1411233080592637954', '1334284783299108865', '1411233080567472130', '2021-07-14', '2023-07-13', '2021-11-01', '1', '1', '2021-07-03 15:59:21', null, null, null, null);
INSERT INTO `payment_contract_fee` VALUES ('1411233080601026561', '6d81c6bc842e4d8ebfdfbd653b5e1f64', '1411233080567472130', '2021-07-14', '2023-07-13', '2022-07-03', '1', '1', '2021-07-03 15:59:21', null, null, null, null);
INSERT INTO `payment_contract_fee` VALUES ('1411233080609415170', '99bcebcea39444bca429a18a413aabdd', '1411233080567472130', '2021-07-14', '2023-07-13', '2022-06-09', '1', '1', '2021-07-03 15:59:21', null, null, null, null);
INSERT INTO `payment_contract_fee` VALUES ('1411233525994168322', '6d81c6bc842e4d8ebfdfbd653b5e1f64', '1411233525880922113', '2021-07-06', null, '2022-07-03', '1', '1', '2021-07-03 16:01:07', null, null, null, null);
INSERT INTO `payment_contract_fee` VALUES ('1411233526002556929', '1334284783299108865', '1411233525880922113', '2021-07-06', null, '2021-10-01', '1', '1', '2021-07-03 16:01:07', null, null, null, null);
INSERT INTO `payment_contract_fee` VALUES ('1411234413265625090', '6d81c6bc842e4d8ebfdfbd653b5e1f64', '1411234413177544705', '2021-07-06', null, '2022-07-03', '1', '1', '2021-07-03 16:04:38', null, null, null, null);
INSERT INTO `payment_contract_fee` VALUES ('1411234413286596609', '1334284783299108865', '1411234413177544705', '2021-07-06', null, '2021-10-01', '1', '1', '2021-07-03 16:04:38', null, null, null, null);
INSERT INTO `payment_contract_fee` VALUES ('1411234730120126466', '6d81c6bc842e4d8ebfdfbd653b5e1f64', '1411234730111737858', '2021-07-16', null, '2022-07-03', '1', '1', '2021-07-03 16:05:54', null, null, null, null);
INSERT INTO `payment_contract_fee` VALUES ('1411234730128515073', '1334284783299108865', '1411234730111737858', '2021-07-16', null, '2021-10-01', '1', '1', '2021-07-03 16:05:54', null, null, null, null);

-- ----------------------------
-- Table structure for `payment_deposit`
-- ----------------------------
DROP TABLE IF EXISTS `payment_deposit`;
CREATE TABLE `payment_deposit` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `resource_name` varchar(50) DEFAULT NULL COMMENT '资源名',
  `resource_id` varchar(100) NOT NULL COMMENT '资源id',
  `block` varchar(50) NOT NULL COMMENT '商铺区域',
  `fee_item_id` varchar(50) NOT NULL,
  `fee_item_name` varchar(50) NOT NULL COMMENT '收费项名',
  `fee_user` varchar(50) NOT NULL COMMENT '客户姓名',
  `amt` decimal(10,2) NOT NULL,
  `pay_type` varchar(50) NOT NULL COMMENT '付款方式',
  `refund_type` varchar(50) NOT NULL DEFAULT '',
  `operate_user` varchar(50) NOT NULL COMMENT '收款人',
  `operate_time` date NOT NULL COMMENT '收款时间',
  `refund_user` varchar(50) DEFAULT NULL COMMENT '退款人',
  `refund_time` date DEFAULT NULL COMMENT '退款时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `pay_no` varchar(200) NOT NULL DEFAULT '' COMMENT '支付单号',
  `refund_no` varchar(200) NOT NULL DEFAULT '' COMMENT '退款单号',
  `state` varchar(10) NOT NULL COMMENT '状态',
  `creator_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator_user_id` varchar(50) DEFAULT NULL COMMENT '创建用户',
  `last_modify_user_id` varchar(50) DEFAULT NULL COMMENT '修改用户',
  `last_modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='押金表';

-- ----------------------------
-- Records of payment_deposit
-- ----------------------------
INSERT INTO `payment_deposit` VALUES ('1411239722449182722', '01-001', '1466607490492850177', '01', '1456543121520291842', '装修保证金', '张一', '200.00', '9d46e7f3729843ef8dc35f9987f7f2fe', '', '1', '2021-07-03', null, null, null, 'PAY2021070300201', '', 'paied', '2021-07-03 16:25:44', '1', null, null);
INSERT INTO `payment_deposit` VALUES ('1411239852090925057', '01-002', '1466607490526404610', '01', '1456543121520291842', '装修保证金', '张二', '2000.00', '13e7e17124b0404bb6411c4d11432cdb', '', '1', '2021-07-03', null, null, null, 'PAY2021070300202', '', 'paied', '2021-07-03 16:26:15', '1', null, null);
INSERT INTO `payment_deposit` VALUES ('1411247780399226882', '01-003', '1466607490547376129', '01', '1456543121520291842', '装修保证金', '张三', '2000.00', '9d46e7f3729843ef8dc35f9987f7f2fe', '', '1', '2021-07-03', null, null, null, 'PAY2021070300203', '', 'paied', '2021-07-03 16:57:45', '1', null, null);
INSERT INTO `payment_deposit` VALUES ('1411247850179862529', '01-004', '1466607490555764737', '01', '1456543121520291842', '装修保证金', '张四', '2000.00', '8bfe0cd9d0744f3e859a68c1983d3c47', '', '1', '2021-07-03', null, null, null, 'PAY2021070300204', '', 'paied', '2021-07-03 16:58:02', '1', null, null);
INSERT INTO `payment_deposit` VALUES ('1411247924582621185', '01-005', '1466607490597707778', '01', '1456543121520291842', '装修保证金', '张武', '2000.00', '9d46e7f3729843ef8dc35f9987f7f2fe', '', '1', '2021-07-03', null, null, null, 'PAY2021070300205', '', 'paied', '2021-07-03 16:58:20', '1', null, null);
INSERT INTO `payment_deposit` VALUES ('1411247988520591362', '01-006', '1466607490606096385', '01', '1456543121520291842', '装修保证金', '张六', '2000.00', '13e7e17124b0404bb6411c4d11432cdb', '', '1', '2021-07-03', null, null, null, 'PAY2021070300206', '', 'paied', '2021-07-03 16:58:35', '1', null, null);
INSERT INTO `payment_deposit` VALUES ('1411248062059323394', '01-007', '1466607490622873602', '01', '1456543121520291842', '装修保证金', '张琪', '2000.00', '9d46e7f3729843ef8dc35f9987f7f2fe', '', '1', '2021-07-03', null, null, null, 'PAY2021070300207', '', 'paied', '2021-07-03 16:58:52', '1', null, null);
INSERT INTO `payment_deposit` VALUES ('1411248324266237954', '01-008', '1466607490631262210', '01', '1456543121520291842', '装修保证金', '丈八', '2000.00', '9d46e7f3729843ef8dc35f9987f7f2fe', '', '1', '2021-07-03', null, null, null, 'PAY2021070300208', '', 'paied', '2021-07-03 16:59:55', '1', null, null);
INSERT INTO `payment_deposit` VALUES ('1411248640491593730', '01-009', '1466607490648039426', '01', '1456543121520291842', '装修保证金', '张久', '2000.00', '9d46e7f3729843ef8dc35f9987f7f2fe', '', '1', '2021-07-03', null, null, null, 'PAY2021070300209', '', 'paied', '2021-07-03 17:01:10', '1', null, null);
INSERT INTO `payment_deposit` VALUES ('1411248702823145474', '01-010', '1466607490656428033', '01', '1456543121520291842', '装修保证金', '张氏', '2000.00', '9d46e7f3729843ef8dc35f9987f7f2fe', '', '1', '2021-07-03', null, null, null, 'PAY2021070300210', '', 'paied', '2021-07-03 17:01:25', '1', null, null);
INSERT INTO `payment_deposit` VALUES ('1416370449671069697', '01-001', '1466607490492850177', '01', 'c20e0daa4d454ae6977099d6559a813a', '履约保证金', '张一', '400.00', '9d46e7f3729843ef8dc35f9987f7f2fe', '13e7e17124b0404bb6411c4d11432cdb', '1', '2021-07-17', '1', '2021-07-17', null, 'PAY2021071700207', 'PAY2021071700208', 'refunded', '2021-07-17 20:13:25', '1', '1', '2021-07-17 20:13:33');

-- ----------------------------
-- Table structure for `payment_meter`
-- ----------------------------
DROP TABLE IF EXISTS `payment_meter`;
CREATE TABLE `payment_meter` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `block_code` varchar(255) NOT NULL COMMENT '区域编码',
  `resource_id` varchar(255) NOT NULL COMMENT '收费的资源',
  `resource_type` varchar(255) NOT NULL COMMENT '资源类型',
  `resource_name` varchar(255) NOT NULL COMMENT '资源名',
  `fee_item_id` varchar(255) NOT NULL COMMENT '收费项id',
  `fee_item_name` varchar(255) NOT NULL COMMENT '收费项名',
  `current_index_date` date NOT NULL COMMENT '当期读表时间',
  `current_index` decimal(10,2) NOT NULL COMMENT '当期读数',
  `last_index` decimal(10,2) NOT NULL COMMENT '上期读数',
  `last_index_date` date DEFAULT NULL COMMENT '上期读表时间',
  `multiple` decimal(4,2) NOT NULL COMMENT '倍率',
  `loss` decimal(10,2) NOT NULL COMMENT '损耗',
  `result` decimal(10,2) NOT NULL,
  `creator_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator_user_id` varchar(50) DEFAULT NULL COMMENT '创建用户',
  `last_modify_user_id` varchar(50) DEFAULT NULL COMMENT '修改用户',
  `last_modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `delete_user_id` varchar(50) DEFAULT NULL COMMENT '删除用户',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `enabled_mark` int(11) NOT NULL COMMENT '有效标志',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='抄表管理';

-- ----------------------------
-- Records of payment_meter
-- ----------------------------

-- ----------------------------
-- Table structure for `payment_meter_index`
-- ----------------------------
DROP TABLE IF EXISTS `payment_meter_index`;
CREATE TABLE `payment_meter_index` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `block_code` varchar(255) NOT NULL COMMENT '区域编码',
  `resource_id` varchar(255) NOT NULL COMMENT '收费的资源',
  `resource_type` varchar(255) NOT NULL COMMENT '资源类型',
  `resource_name` varchar(255) NOT NULL COMMENT '资源名',
  `fee_item_id` varchar(255) NOT NULL COMMENT '收费项id',
  `current_index_date` date NOT NULL COMMENT '读数时间',
  `current_index` int(10) NOT NULL COMMENT '当前表读数',
  `multiple` decimal(4,2) NOT NULL,
  `creator_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator_user_id` varchar(50) DEFAULT NULL COMMENT '创建用户',
  `last_modify_user_id` varchar(50) DEFAULT NULL COMMENT '修改用户',
  `last_modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `meter_index_resource_fee` (`resource_id`,`fee_item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='抄表的最新表数';

-- ----------------------------
-- Records of payment_meter_index
-- ----------------------------
INSERT INTO `payment_meter_index` VALUES ('1416330940954779649', '01', '1466607490492850177', 'house', '01-001', '1334284783299108865', '2021-10-15', '300', '1.00', '2021-07-17 17:36:25', '1', null, null);
INSERT INTO `payment_meter_index` VALUES ('1416330940954779650', '01', '1466607490526404610', 'house', '01-002', '1334284783299108865', '2021-10-15', '330', '1.00', '2021-07-17 17:36:25', '1', null, null);
INSERT INTO `payment_meter_index` VALUES ('1416330940963168258', '01', '1466607490547376129', 'house', '01-003', '1334284783299108865', '2021-10-15', '170', '1.00', '2021-07-17 17:36:25', '1', null, null);
INSERT INTO `payment_meter_index` VALUES ('1416330940963168259', '01', '1466607490555764737', 'house', '01-004', '1334284783299108865', '2021-10-15', '2', '1.10', '2021-07-17 17:36:25', '1', '1', '2021-07-17 17:49:11');
INSERT INTO `payment_meter_index` VALUES ('1416330940963168260', '01', '1466607490597707778', 'house', '01-005', '1334284783299108865', '2021-10-15', '40', '1.00', '2021-07-17 17:36:25', '1', null, null);
INSERT INTO `payment_meter_index` VALUES ('1416330940971556866', '01', '1466607490606096385', 'house', '01-006', '1334284783299108865', '2021-10-15', '200', '1.00', '2021-07-17 17:36:25', '1', null, null);
INSERT INTO `payment_meter_index` VALUES ('1416330940971556867', '01', '1466607490622873602', 'house', '01-007', '1334284783299108865', '2021-10-15', '200', '1.00', '2021-07-17 17:36:25', '1', null, null);
INSERT INTO `payment_meter_index` VALUES ('1416330940979945473', '01', '1466607490631262210', 'house', '01-008', '1334284783299108865', '2021-09-15', '320', '1.00', '2021-07-17 17:36:25', '1', null, null);
INSERT INTO `payment_meter_index` VALUES ('1416330940988334082', '01', '1466607490648039426', 'house', '01-009', '1334284783299108865', '2021-09-15', '270', '1.00', '2021-07-17 17:36:25', '1', null, null);
INSERT INTO `payment_meter_index` VALUES ('1416330940988334083', '01', '1466607490656428033', 'house', '01-010', '1334284783299108865', '2021-09-15', '200', '1.00', '2021-07-17 17:36:25', '1', null, null);

-- ----------------------------
-- Table structure for `payment_pay_log`
-- ----------------------------
DROP TABLE IF EXISTS `payment_pay_log`;
CREATE TABLE `payment_pay_log` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `resource_name` varchar(255) DEFAULT NULL COMMENT '编号',
  `fee_type` varchar(20) NOT NULL COMMENT '费用类型（临时费、商铺费、押金）',
  `type` varchar(50) NOT NULL COMMENT '支付和退款',
  `pay_no` varchar(50) NOT NULL COMMENT '流水单号',
  `pay_time` datetime NOT NULL COMMENT '付款时间',
  `pay_method` varchar(50) NOT NULL COMMENT '支付方式',
  `Item_total_money` decimal(11,2) NOT NULL,
  `late_fee_money` decimal(11,2) NOT NULL COMMENT '滞纳金',
  `receivable_money` decimal(11,2) NOT NULL COMMENT '应收金额',
  `discount_money` decimal(11,2) NOT NULL COMMENT '优惠金额',
  `pay_money` decimal(11,2) NOT NULL COMMENT '支付金额',
  `pre_pay_money` decimal(11,2) NOT NULL COMMENT '预付款支付金额',
  `change_money` decimal(11,2) NOT NULL COMMENT '找零',
  `pre_save_money` decimal(11,2) NOT NULL COMMENT '预存金额',
  `name` varchar(200) NOT NULL COMMENT '描述',
  `certificate` varchar(200) DEFAULT NULL COMMENT '支付凭证',
  `comment` varchar(200) DEFAULT NULL,
  `creator_user_id` varchar(50) DEFAULT NULL COMMENT '创建用户',
  `creator_time` datetime DEFAULT NULL COMMENT '创建时间',
  `payer_name` varchar(50) NOT NULL,
  `payer_idcard` varchar(18) NOT NULL,
  `payer_phone` varchar(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_pay_log_no` (`pay_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付流水表';

-- ----------------------------
-- Records of payment_pay_log
-- ----------------------------
INSERT INTO `payment_pay_log` VALUES ('1411239722415628289', '01-001', 'deposit', 'pay', 'PAY2021070300201', '2021-07-03 16:25:44', '9d46e7f3729843ef8dc35f9987f7f2fe', '200.00', '0.00', '200.00', '0.00', '200.00', '0.00', '0.00', '0.00', '装修保证金', '', '', '1', '2021-07-03 16:25:44', '张一', '', '');
INSERT INTO `payment_pay_log` VALUES ('1411239852082536449', '01-002', 'deposit', 'pay', 'PAY2021070300202', '2021-07-03 16:26:15', '13e7e17124b0404bb6411c4d11432cdb', '2000.00', '0.00', '2000.00', '0.00', '2000.00', '0.00', '0.00', '0.00', '装修保证金', '', '', '1', '2021-07-03 16:26:15', '张二', '', '');
INSERT INTO `payment_pay_log` VALUES ('1411247780382449666', '01-003', 'deposit', 'pay', 'PAY2021070300203', '2021-07-03 16:57:45', '9d46e7f3729843ef8dc35f9987f7f2fe', '2000.00', '0.00', '2000.00', '0.00', '2000.00', '0.00', '0.00', '0.00', '装修保证金', '', '', '1', '2021-07-03 16:57:45', '张三', '', '');
INSERT INTO `payment_pay_log` VALUES ('1411247850171473922', '01-004', 'deposit', 'pay', 'PAY2021070300204', '2021-07-03 16:58:02', '8bfe0cd9d0744f3e859a68c1983d3c47', '2000.00', '0.00', '2000.00', '0.00', '2000.00', '0.00', '0.00', '0.00', '装修保证金', '', '', '1', '2021-07-03 16:58:02', '张四', '', '');
INSERT INTO `payment_pay_log` VALUES ('1411247924557455362', '01-005', 'deposit', 'pay', 'PAY2021070300205', '2021-07-03 16:58:20', '9d46e7f3729843ef8dc35f9987f7f2fe', '2000.00', '0.00', '2000.00', '0.00', '2000.00', '0.00', '0.00', '0.00', '装修保证金', '', '', '1', '2021-07-03 16:58:20', '张武', '', '');
INSERT INTO `payment_pay_log` VALUES ('1411247988503814146', '01-006', 'deposit', 'pay', 'PAY2021070300206', '2021-07-03 16:58:35', '13e7e17124b0404bb6411c4d11432cdb', '2000.00', '0.00', '2000.00', '0.00', '2000.00', '0.00', '0.00', '0.00', '装修保证金', '', '', '1', '2021-07-03 16:58:35', '张六', '', '');
INSERT INTO `payment_pay_log` VALUES ('1411248062029963265', '01-007', 'deposit', 'pay', 'PAY2021070300207', '2021-07-03 16:58:52', '9d46e7f3729843ef8dc35f9987f7f2fe', '2000.00', '0.00', '2000.00', '0.00', '2000.00', '0.00', '0.00', '0.00', '装修保证金', '', '', '1', '2021-07-03 16:58:52', '张琪', '', '');
INSERT INTO `payment_pay_log` VALUES ('1411248324245266433', '01-008', 'deposit', 'pay', 'PAY2021070300208', '2021-07-03 16:59:55', '9d46e7f3729843ef8dc35f9987f7f2fe', '2000.00', '0.00', '2000.00', '0.00', '2000.00', '0.00', '0.00', '0.00', '装修保证金', '', '', '1', '2021-07-03 16:59:55', '丈八', '', '');
INSERT INTO `payment_pay_log` VALUES ('1411248640483205122', '01-009', 'deposit', 'pay', 'PAY2021070300209', '2021-07-03 17:01:10', '9d46e7f3729843ef8dc35f9987f7f2fe', '2000.00', '0.00', '2000.00', '0.00', '2000.00', '0.00', '0.00', '0.00', '装修保证金', '', '', '1', '2021-07-03 17:01:10', '张久', '', '');
INSERT INTO `payment_pay_log` VALUES ('1411248702810562562', '01-010', 'deposit', 'pay', 'PAY2021070300210', '2021-07-03 17:01:25', '9d46e7f3729843ef8dc35f9987f7f2fe', '2000.00', '0.00', '2000.00', '0.00', '2000.00', '0.00', '0.00', '0.00', '装修保证金', '', '', '1', '2021-07-03 17:01:25', '张氏', '', '');
INSERT INTO `payment_pay_log` VALUES ('1411249182257258497', '', 'deposit', 'pay', 'PAY2021070300211', '2021-07-03 17:03:19', '13e7e17124b0404bb6411c4d11432cdb', '20000.00', '0.00', '20000.00', '0.00', '20000.00', '0.00', '0.00', '0.00', '广告费用（公共区域）', '', '', '1', '2021-07-03 17:03:19', '王老五', '', '');
INSERT INTO `payment_pay_log` VALUES ('1416368224999645185', '01-001', 'house', 'pay', 'PAY2021071700201', '2021-07-17 20:04:34', '13e7e17124b0404bb6411c4d11432cdb', '13245.00', '0.00', '13245.00', '0.00', '4000.00', '10000.00', '0.00', '755.00', '物业管理费,抄表水费', '', '', '1', '2021-07-17 20:04:34', '张一', '140702198502036501', '13265412321');
INSERT INTO `payment_pay_log` VALUES ('1416369066154729473', '01-001', 'deposit', 'pay', 'PAY2021071700205', '2021-07-17 20:07:55', '9d46e7f3729843ef8dc35f9987f7f2fe', '500.00', '0.00', '500.00', '0.00', '500.00', '0.00', '0.00', '0.00', '装修垃圾清运费', '', '', '1', '2021-07-17 20:07:55', '张一', '', '');
INSERT INTO `payment_pay_log` VALUES ('1416370316308979713', null, 'temp', 'refund', 'PAY2021071700206', '2021-07-17 20:12:53', '13e7e17124b0404bb6411c4d11432cdb', '-500.00', '0.00', '-500.00', '0.00', '-500.00', '0.00', '0.00', '0.00', '装修垃圾清运费', '', '', '1', '2021-07-17 20:12:53', '张一', '', '');
INSERT INTO `payment_pay_log` VALUES ('1416370449662681090', '01-001', 'deposit', 'pay', 'PAY2021071700207', '2021-07-17 20:13:25', '9d46e7f3729843ef8dc35f9987f7f2fe', '400.00', '0.00', '400.00', '0.00', '400.00', '0.00', '0.00', '0.00', '履约保证金', '', '', '1', '2021-07-17 20:13:25', '张一', '', '');
INSERT INTO `payment_pay_log` VALUES ('1416370484991303682', '01-001', 'deposit', 'refund', 'PAY2021071700208', '2021-07-17 20:13:33', '13e7e17124b0404bb6411c4d11432cdb', '-400.00', '0.00', '-400.00', '0.00', '-400.00', '0.00', '0.00', '0.00', '履约保证金', '', '', '1', '2021-07-17 20:13:33', '张一', '', '');
INSERT INTO `payment_pay_log` VALUES ('1427823644477759490', '01-001', 'house', 'pay', 'PAY2021081800201', '2021-08-18 10:44:19', '8bfe0cd9d0744f3e859a68c1983d3c47', '9.00', '0.00', '9.00', '0.00', '0.00', '9.00', '0.00', '0.00', '抄表水费', '', '', '1', '2021-08-18 10:44:19', '张一', '140702198502036501', '13265412321');
INSERT INTO `payment_pay_log` VALUES ('1427832441472557057', '01-003', 'house', 'pay', 'PAY2021081800204', '2021-08-18 11:19:17', '9d46e7f3729843ef8dc35f9987f7f2fe', '17173.95', '0.00', '17173.95', '0.00', '17173.95', '0.00', '0.00', '0.00', '物业管理费,抄表水费等...', '', '', '1', '2021-08-18 11:19:17', '张三', '140702195603032603', '13562356253');
INSERT INTO `payment_pay_log` VALUES ('1427832903064100866', '01-004', 'house', 'pay', 'PAY2021081800205', '2021-08-18 11:21:07', '9d46e7f3729843ef8dc35f9987f7f2fe', '229140.50', '0.00', '219140.50', '10000.00', '219140.50', '0.00', '0.00', '0.00', '物业管理费,抄表水费等...', '', '', '1', '2021-08-18 11:21:07', '张四', '140236198702023256', '13651325896');
INSERT INTO `payment_pay_log` VALUES ('1427833133427859457', '01-005', 'house', 'pay', 'PAY2021081800206', '2021-08-18 11:22:02', '9d46e7f3729843ef8dc35f9987f7f2fe', '21135.75', '0.00', '21135.75', '0.00', '21135.75', '0.00', '0.00', '0.00', '物业管理费,抄表水费', '', '', '1', '2021-08-18 11:22:02', '张武', '140702198702036505', '13684652365');
INSERT INTO `payment_pay_log` VALUES ('1439069562287955970', '01-001', 'house', 'pay', 'PAY2021091800201', '2021-09-18 11:31:35', '9d46e7f3729843ef8dc35f9987f7f2fe', '36.00', '0.00', '36.00', '0.00', '0.00', '36.00', '0.00', '0.00', '抄表水费', '', '', '1', '2021-09-18 11:31:35', '张一', '140702198502036501', '13265412321');
INSERT INTO `payment_pay_log` VALUES ('1439069972805459969', '01-002', 'house', 'pay', 'PAY2021091800204', '2021-09-18 11:33:13', '744e2f652ad24b7eaa18d00cf134136a', '165391.50', '0.00', '165391.50', '0.00', '200000.00', '0.00', '0.00', '34608.50', '物业管理费,抄表水费等...', '', '', '1', '2021-09-18 11:33:13', '张二', '140702195603023202', '13561325653');

-- ----------------------------
-- Table structure for `payment_pre`
-- ----------------------------
DROP TABLE IF EXISTS `payment_pre`;
CREATE TABLE `payment_pre` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `resource_name` varchar(50) DEFAULT NULL COMMENT '资源名',
  `resource_id` varchar(100) NOT NULL COMMENT '资源id',
  `block` varchar(50) NOT NULL COMMENT '商铺区域',
  `fee_item_name` varchar(50) NOT NULL COMMENT '收费项名',
  `fee_item_id` varchar(50) NOT NULL,
  `fee_user` varchar(50) NOT NULL COMMENT '客户姓名',
  `amt` decimal(10,2) NOT NULL,
  `pay_type` varchar(50) NOT NULL COMMENT '付款方式',
  `type` varchar(255) NOT NULL COMMENT '支取还是收款',
  `use_fee_item` char(1) NOT NULL COMMENT '是否指定收费项',
  `operate_user` varchar(50) NOT NULL COMMENT '收款人',
  `operate_time` date NOT NULL COMMENT '收款时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `pay_no` varchar(200) NOT NULL DEFAULT '' COMMENT '支付单号',
  `creator_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator_user_id` varchar(50) DEFAULT NULL COMMENT '创建用户',
  `last_modify_user_id` varchar(50) DEFAULT NULL COMMENT '修改用户',
  `last_modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='押金表';

-- ----------------------------
-- Records of payment_pre
-- ----------------------------
INSERT INTO `payment_pre` VALUES ('1411251277710565377', '01-001', '1466607490492850177', '01', '物业管理费', '6d81c6bc842e4d8ebfdfbd653b5e1f64', '张一', '10000.00', '9d46e7f3729843ef8dc35f9987f7f2fe', 'add', '1', '1', '2021-07-03', null, 'PAY2021070300212', null, null, null, null);
INSERT INTO `payment_pre` VALUES ('1416368225104502785', '01-001', '1466607490492850177', '01', '物业管理费', '6d81c6bc842e4d8ebfdfbd653b5e1f64', '张一', '-10000.00', '', 'pay', '1', '1', '2021-07-17', null, 'PAY2021071700202', null, null, null, null);
INSERT INTO `payment_pre` VALUES ('1416368225284857858', '01-001', '1466607490492850177', '01', '不指定收费项', '', '张一', '755.00', '13e7e17124b0404bb6411c4d11432cdb', 'payAdd', '0', '1', '2021-07-17', null, 'PAY2021071700204', null, null, null, null);
INSERT INTO `payment_pre` VALUES ('1427823644570034177', '01-001', '1466607490492850177', '01', '物业管理费', '6d81c6bc842e4d8ebfdfbd653b5e1f64', '张一', '0.00', '', 'pay', '1', '1', '2021-08-18', null, 'PAY2021081800202', null, null, null, null);
INSERT INTO `payment_pre` VALUES ('1427823644645531650', '01-001', '1466607490492850177', '01', '不指定收费项', '', '张一', '-9.00', '', 'pay', '0', '1', '2021-08-18', null, 'PAY2021081800203', null, null, null, null);
INSERT INTO `payment_pre` VALUES ('1439069562380230658', '01-001', '1466607490492850177', '01', '物业管理费', '6d81c6bc842e4d8ebfdfbd653b5e1f64', '张一', '0.00', '', 'pay', '1', '1', '2021-09-18', null, 'PAY2021091800202', null, null, null, null);
INSERT INTO `payment_pre` VALUES ('1439069562443145217', '01-001', '1466607490492850177', '01', '不指定收费项', '', '张一', '-36.00', '', 'pay', '0', '1', '2021-09-18', null, 'PAY2021091800203', null, null, null, null);
INSERT INTO `payment_pre` VALUES ('1439069973031952386', '01-002', '1466607490526404610', '01', '不指定收费项', '', '张二', '34608.50', '744e2f652ad24b7eaa18d00cf134136a', 'payAdd', '0', '1', '2021-09-18', null, 'PAY2021091800206', null, null, null, null);

-- ----------------------------
-- Table structure for `payment_pre_account`
-- ----------------------------
DROP TABLE IF EXISTS `payment_pre_account`;
CREATE TABLE `payment_pre_account` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `resource_name` varchar(50) DEFAULT NULL COMMENT '资源名',
  `resource_id` varchar(100) NOT NULL COMMENT '资源id',
  `block` varchar(50) NOT NULL COMMENT '商铺区域',
  `use_fee_item` char(1) NOT NULL,
  `fee_item_id` varchar(50) NOT NULL,
  `amt` decimal(10,2) NOT NULL,
  `creator_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator_user_id` varchar(50) DEFAULT NULL COMMENT '创建用户',
  `last_modify_user_id` varchar(50) DEFAULT NULL COMMENT '修改用户',
  `last_modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pre_account_index_unique` (`resource_id`,`fee_item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='预存款余额账户';

-- ----------------------------
-- Records of payment_pre_account
-- ----------------------------
INSERT INTO `payment_pre_account` VALUES ('1411251277693788161', '01-001', '1466607490492850177', '01', '1', '6d81c6bc842e4d8ebfdfbd653b5e1f64', '0.00', '2021-07-03 17:11:39', '1', null, null);
INSERT INTO `payment_pre_account` VALUES ('1416368225268080641', '01-001', '1466607490492850177', '01', '0', '', '710.00', '2021-07-17 20:04:34', '1', null, null);
INSERT INTO `payment_pre_account` VALUES ('1439069973015175170', '01-002', '1466607490526404610', '01', '0', '', '34608.50', '2021-09-18 11:33:13', '1', null, null);

-- ----------------------------
-- Table structure for `payment_temp`
-- ----------------------------
DROP TABLE IF EXISTS `payment_temp`;
CREATE TABLE `payment_temp` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `resource_name` varchar(50) DEFAULT NULL COMMENT '资源名',
  `resource_id` varchar(100) NOT NULL COMMENT '资源id',
  `block` varchar(50) NOT NULL COMMENT '商铺区域',
  `fee_item_id` varchar(50) NOT NULL,
  `fee_item_name` varchar(50) NOT NULL COMMENT '收费项名',
  `fee_user` varchar(50) NOT NULL COMMENT '客户姓名',
  `amt` decimal(10,2) NOT NULL,
  `pay_type` varchar(50) NOT NULL COMMENT '付款方式',
  `refund_type` varchar(50) NOT NULL DEFAULT '',
  `operate_user` varchar(50) NOT NULL COMMENT '收款人',
  `operate_time` date NOT NULL COMMENT '收款时间',
  `refund_user` varchar(50) DEFAULT NULL COMMENT '退款人',
  `refund_time` date DEFAULT NULL COMMENT '退款时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `pay_no` varchar(200) NOT NULL DEFAULT '' COMMENT '支付单号',
  `refund_no` varchar(200) NOT NULL DEFAULT '' COMMENT '退款单号',
  `state` varchar(10) NOT NULL COMMENT '状态',
  `creator_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator_user_id` varchar(50) DEFAULT NULL COMMENT '创建用户',
  `last_modify_user_id` varchar(50) DEFAULT NULL COMMENT '修改用户',
  `last_modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='押金表';

-- ----------------------------
-- Records of payment_temp
-- ----------------------------
INSERT INTO `payment_temp` VALUES ('1411249182274035713', '', '', '', '1456546272025604098', '广告费用（公共区域）', '王老五', '20000.00', '13e7e17124b0404bb6411c4d11432cdb', '', '1', '2021-07-03', null, null, null, 'PAY2021070300211', '', 'paied', '2021-07-03 17:03:19', '1', null, null);
INSERT INTO `payment_temp` VALUES ('1416369066163118081', '01-001', '1466607490492850177', '01', '1456545461837066241', '装修垃圾清运费', '张一', '500.00', '9d46e7f3729843ef8dc35f9987f7f2fe', '13e7e17124b0404bb6411c4d11432cdb', '1', '2021-07-17', '1', '2021-07-17', '321', 'PAY2021071700205', 'PAY2021071700206', 'refunded', '2021-07-17 20:07:55', '1', '1', '2021-07-17 20:12:53');

-- ----------------------------
-- Table structure for `qrtz_blob_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `blob_data` blob COMMENT '存放持久化Trigger对象',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Blob类型的触发器表';

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for `qrtz_calendars`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `calendar_name` varchar(200) NOT NULL COMMENT '日历名称',
  `calendar` blob NOT NULL COMMENT '存放持久化calendar对象',
  PRIMARY KEY (`sched_name`,`calendar_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日历信息表';

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------

-- ----------------------------
-- Table structure for `qrtz_cron_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `cron_expression` varchar(200) NOT NULL COMMENT 'cron表达式',
  `time_zone_id` varchar(80) DEFAULT NULL COMMENT '时区',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Cron类型的触发器表';

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
INSERT INTO `qrtz_cron_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME1', 'DEFAULT', '0/10 * * * * ?', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME2', 'DEFAULT', '0/15 * * * * ?', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME3', 'DEFAULT', '0/20 * * * * ?', 'Asia/Shanghai');

-- ----------------------------
-- Table structure for `qrtz_fired_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `entry_id` varchar(95) NOT NULL COMMENT '调度器实例id',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `instance_name` varchar(200) NOT NULL COMMENT '调度器实例名',
  `fired_time` bigint(13) NOT NULL COMMENT '触发的时间',
  `sched_time` bigint(13) NOT NULL COMMENT '定时器制定的时间',
  `priority` int(11) NOT NULL COMMENT '优先级',
  `state` varchar(16) NOT NULL COMMENT '状态',
  `job_name` varchar(200) DEFAULT NULL COMMENT '任务名称',
  `job_group` varchar(200) DEFAULT NULL COMMENT '任务组名',
  `is_nonconcurrent` varchar(1) DEFAULT NULL COMMENT '是否并发',
  `requests_recovery` varchar(1) DEFAULT NULL COMMENT '是否接受恢复执行',
  PRIMARY KEY (`sched_name`,`entry_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='已触发的触发器表';

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for `qrtz_job_details`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `job_name` varchar(200) NOT NULL COMMENT '任务名称',
  `job_group` varchar(200) NOT NULL COMMENT '任务组名',
  `description` varchar(250) DEFAULT NULL COMMENT '相关介绍',
  `job_class_name` varchar(250) NOT NULL COMMENT '执行任务类名称',
  `is_durable` varchar(1) NOT NULL COMMENT '是否持久化',
  `is_nonconcurrent` varchar(1) NOT NULL COMMENT '是否并发',
  `is_update_data` varchar(1) NOT NULL COMMENT '是否更新数据',
  `requests_recovery` varchar(1) NOT NULL COMMENT '是否接受恢复执行',
  `job_data` blob COMMENT '存放持久化job对象',
  PRIMARY KEY (`sched_name`,`job_name`,`job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务详细信息表';

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
INSERT INTO `qrtz_job_details` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME1', 'DEFAULT', null, 'com.zhaoxinms.quartz.util.QuartzDisallowConcurrentExecution', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000F5441534B5F50524F504552544945537372001E636F6D2E72756F79692E71756172747A2E646F6D61696E2E5379734A6F6200000000000000010200084C000A636F6E63757272656E747400124C6A6176612F6C616E672F537472696E673B4C000E63726F6E45787072657373696F6E71007E00094C000C696E766F6B6554617267657471007E00094C00086A6F6247726F757071007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C00076A6F624E616D6571007E00094C000D6D697366697265506F6C69637971007E00094C000673746174757371007E000978720027636F6D2E72756F79692E636F6D6D6F6E2E636F72652E646F6D61696E2E42617365456E7469747900000000000000010200074C0008637265617465427971007E00094C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C0006706172616D7371007E00034C000672656D61726B71007E00094C000B73656172636856616C756571007E00094C0008757064617465427971007E00094C000A75706461746554696D6571007E000C787074000561646D696E7372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000017D0E91118078707400007070707400013174000E302F3130202A202A202A202A203F74001172795461736B2E72794E6F506172616D7374000744454641554C547372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000000000001740018E7B3BBE7BB9FE9BB98E8AEA4EFBC88E697A0E58F82EFBC8974000133740001317800);
INSERT INTO `qrtz_job_details` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME2', 'DEFAULT', null, 'com.zhaoxinms.quartz.util.QuartzDisallowConcurrentExecution', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000F5441534B5F50524F504552544945537372001E636F6D2E72756F79692E71756172747A2E646F6D61696E2E5379734A6F6200000000000000010200084C000A636F6E63757272656E747400124C6A6176612F6C616E672F537472696E673B4C000E63726F6E45787072657373696F6E71007E00094C000C696E766F6B6554617267657471007E00094C00086A6F6247726F757071007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C00076A6F624E616D6571007E00094C000D6D697366697265506F6C69637971007E00094C000673746174757371007E000978720027636F6D2E72756F79692E636F6D6D6F6E2E636F72652E646F6D61696E2E42617365456E7469747900000000000000010200074C0008637265617465427971007E00094C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C0006706172616D7371007E00034C000672656D61726B71007E00094C000B73656172636856616C756571007E00094C0008757064617465427971007E00094C000A75706461746554696D6571007E000C787074000561646D696E7372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000017D0E91118078707400007070707400013174000E302F3135202A202A202A202A203F74001572795461736B2E7279506172616D7328277279272974000744454641554C547372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000000000002740018E7B3BBE7BB9FE9BB98E8AEA4EFBC88E69C89E58F82EFBC8974000133740001317800);
INSERT INTO `qrtz_job_details` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME3', 'DEFAULT', null, 'com.zhaoxinms.quartz.util.QuartzDisallowConcurrentExecution', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000F5441534B5F50524F504552544945537372001E636F6D2E72756F79692E71756172747A2E646F6D61696E2E5379734A6F6200000000000000010200084C000A636F6E63757272656E747400124C6A6176612F6C616E672F537472696E673B4C000E63726F6E45787072657373696F6E71007E00094C000C696E766F6B6554617267657471007E00094C00086A6F6247726F757071007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C00076A6F624E616D6571007E00094C000D6D697366697265506F6C69637971007E00094C000673746174757371007E000978720027636F6D2E72756F79692E636F6D6D6F6E2E636F72652E646F6D61696E2E42617365456E7469747900000000000000010200074C0008637265617465427971007E00094C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C0006706172616D7371007E00034C000672656D61726B71007E00094C000B73656172636856616C756571007E00094C0008757064617465427971007E00094C000A75706461746554696D6571007E000C787074000561646D696E7372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000017D0E91118078707400007070707400013174000E302F3230202A202A202A202A203F74003872795461736B2E72794D756C7469706C65506172616D7328277279272C20747275652C20323030304C2C203331362E3530442C203130302974000744454641554C547372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000000000003740018E7B3BBE7BB9FE9BB98E8AEA4EFBC88E5A49AE58F82EFBC8974000133740001317800);

-- ----------------------------
-- Table structure for `qrtz_locks`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `lock_name` varchar(40) NOT NULL COMMENT '悲观锁名称',
  PRIMARY KEY (`sched_name`,`lock_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='存储的悲观锁信息表';

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('RuoyiScheduler', 'STATE_ACCESS');
INSERT INTO `qrtz_locks` VALUES ('RuoyiScheduler', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for `qrtz_paused_trigger_grps`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  PRIMARY KEY (`sched_name`,`trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='暂停的触发器表';

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------

-- ----------------------------
-- Table structure for `qrtz_scheduler_state`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `instance_name` varchar(200) NOT NULL COMMENT '实例名称',
  `last_checkin_time` bigint(13) NOT NULL COMMENT '上次检查时间',
  `checkin_interval` bigint(13) NOT NULL COMMENT '检查间隔时间',
  PRIMARY KEY (`sched_name`,`instance_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='调度器状态表';

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
INSERT INTO `qrtz_scheduler_state` VALUES ('RuoyiScheduler', 'LAPTOP-29G1MVEK1639203077113', '1639203276259', '15000');

-- ----------------------------
-- Table structure for `qrtz_simple_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `repeat_count` bigint(7) NOT NULL COMMENT '重复的次数统计',
  `repeat_interval` bigint(12) NOT NULL COMMENT '重复的间隔时间',
  `times_triggered` bigint(10) NOT NULL COMMENT '已经触发的次数',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='简单触发器的信息表';

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for `qrtz_simprop_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `str_prop_1` varchar(512) DEFAULT NULL COMMENT 'String类型的trigger的第一个参数',
  `str_prop_2` varchar(512) DEFAULT NULL COMMENT 'String类型的trigger的第二个参数',
  `str_prop_3` varchar(512) DEFAULT NULL COMMENT 'String类型的trigger的第三个参数',
  `int_prop_1` int(11) DEFAULT NULL COMMENT 'int类型的trigger的第一个参数',
  `int_prop_2` int(11) DEFAULT NULL COMMENT 'int类型的trigger的第二个参数',
  `long_prop_1` bigint(20) DEFAULT NULL COMMENT 'long类型的trigger的第一个参数',
  `long_prop_2` bigint(20) DEFAULT NULL COMMENT 'long类型的trigger的第二个参数',
  `dec_prop_1` decimal(13,4) DEFAULT NULL COMMENT 'decimal类型的trigger的第一个参数',
  `dec_prop_2` decimal(13,4) DEFAULT NULL COMMENT 'decimal类型的trigger的第二个参数',
  `bool_prop_1` varchar(1) DEFAULT NULL COMMENT 'Boolean类型的trigger的第一个参数',
  `bool_prop_2` varchar(1) DEFAULT NULL COMMENT 'Boolean类型的trigger的第二个参数',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='同步机制的行锁表';

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for `qrtz_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT '触发器的名字',
  `trigger_group` varchar(200) NOT NULL COMMENT '触发器所属组的名字',
  `job_name` varchar(200) NOT NULL COMMENT 'qrtz_job_details表job_name的外键',
  `job_group` varchar(200) NOT NULL COMMENT 'qrtz_job_details表job_group的外键',
  `description` varchar(250) DEFAULT NULL COMMENT '相关介绍',
  `next_fire_time` bigint(13) DEFAULT NULL COMMENT '上一次触发时间（毫秒）',
  `prev_fire_time` bigint(13) DEFAULT NULL COMMENT '下一次触发时间（默认为-1表示不触发）',
  `priority` int(11) DEFAULT NULL COMMENT '优先级',
  `trigger_state` varchar(16) NOT NULL COMMENT '触发器状态',
  `trigger_type` varchar(8) NOT NULL COMMENT '触发器的类型',
  `start_time` bigint(13) NOT NULL COMMENT '开始时间',
  `end_time` bigint(13) DEFAULT NULL COMMENT '结束时间',
  `calendar_name` varchar(200) DEFAULT NULL COMMENT '日程表名称',
  `misfire_instr` smallint(2) DEFAULT NULL COMMENT '补偿执行的策略',
  `job_data` blob COMMENT '存放持久化job对象',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  KEY `sched_name` (`sched_name`,`job_name`,`job_group`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `qrtz_job_details` (`sched_name`, `job_name`, `job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='触发器详细信息表';

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
INSERT INTO `qrtz_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME1', 'DEFAULT', 'TASK_CLASS_NAME1', 'DEFAULT', null, '1639203080000', '-1', '5', 'PAUSED', 'CRON', '1639203077000', '0', null, '2', '');
INSERT INTO `qrtz_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME2', 'DEFAULT', 'TASK_CLASS_NAME2', 'DEFAULT', null, '1639203090000', '-1', '5', 'PAUSED', 'CRON', '1639203077000', '0', null, '2', '');
INSERT INTO `qrtz_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME3', 'DEFAULT', 'TASK_CLASS_NAME3', 'DEFAULT', null, '1639203080000', '-1', '5', 'PAUSED', 'CRON', '1639203077000', '0', null, '2', '');

-- ----------------------------
-- Table structure for `sys_config`
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `config_id` int(5) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='参数配置表';

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES ('1', '主框架页-默认皮肤样式名称', 'sys.index.skinName', 'skin-blue', 'Y', 'admin', '2021-11-11 18:35:28', '', null, '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow');
INSERT INTO `sys_config` VALUES ('2', '用户管理-账号初始密码', 'sys.user.initPassword', '123456', 'Y', 'admin', '2021-11-11 18:35:28', '', null, '初始化密码 123456');
INSERT INTO `sys_config` VALUES ('3', '主框架页-侧边栏主题', 'sys.index.sideTheme', 'theme-dark', 'Y', 'admin', '2021-11-11 18:35:28', '', null, '深色主题theme-dark，浅色主题theme-light');
INSERT INTO `sys_config` VALUES ('4', '账号自助-验证码开关', 'sys.account.captchaOnOff', 'true', 'Y', 'admin', '2021-11-11 18:35:28', '', null, '是否开启验证码功能（true开启，false关闭）');
INSERT INTO `sys_config` VALUES ('5', '账号自助-是否开启用户注册功能', 'sys.account.registerUser', 'false', 'Y', 'admin', '2021-11-11 18:35:28', '', null, '是否开启注册用户功能（true开启，false关闭）');

-- ----------------------------
-- Table structure for `sys_dept`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父部门id',
  `ancestors` varchar(50) DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) DEFAULT '' COMMENT '部门名称',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `leader` varchar(20) DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `status` char(1) DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8 COMMENT='部门表';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('100', '0', '0', '肇新科技', '0', '若依', '', 'ry@qq.com', '0', '0', 'admin', '2021-11-11 18:35:24', 'admin', '2021-11-30 18:21:52');
INSERT INTO `sys_dept` VALUES ('101', '100', '0,100', '深圳总公司', '1', '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2021-11-11 18:35:24', '', null);
INSERT INTO `sys_dept` VALUES ('103', '101', '0,100,101', '研发部门', '1', '若依', '15888888888', 'ry@qq.com', '0', '2', 'admin', '2021-11-11 18:35:24', '', null);
INSERT INTO `sys_dept` VALUES ('104', '101', '0,100,101', '市场部门', '2', '若依', '15888888888', 'ry@qq.com', '0', '2', 'admin', '2021-11-11 18:35:24', '', null);
INSERT INTO `sys_dept` VALUES ('105', '101', '0,100,101', '测试部门', '3', '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2021-11-11 18:35:24', '', null);
INSERT INTO `sys_dept` VALUES ('106', '101', '0,100,101', '财务部门', '4', '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2021-11-11 18:35:24', '', null);
INSERT INTO `sys_dept` VALUES ('107', '101', '0,100,101', '运维部门', '5', '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2021-11-11 18:35:24', '', null);

-- ----------------------------
-- Table structure for `sys_dict_data`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data` (
  `dict_code` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int(4) DEFAULT '0' COMMENT '字典排序',
  `dict_label` varchar(100) DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='字典数据表';

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES ('1', '1', '男', '0', 'sys_user_sex', '', '', 'Y', '0', 'admin', '2021-11-11 18:35:28', '', null, '性别男');
INSERT INTO `sys_dict_data` VALUES ('2', '2', '女', '1', 'sys_user_sex', '', '', 'N', '0', 'admin', '2021-11-11 18:35:28', '', null, '性别女');
INSERT INTO `sys_dict_data` VALUES ('3', '3', '未知', '2', 'sys_user_sex', '', '', 'N', '0', 'admin', '2021-11-11 18:35:28', '', null, '性别未知');
INSERT INTO `sys_dict_data` VALUES ('4', '1', '显示', '0', 'sys_show_hide', '', 'primary', 'Y', '0', 'admin', '2021-11-11 18:35:28', '', null, '显示菜单');
INSERT INTO `sys_dict_data` VALUES ('5', '2', '隐藏', '1', 'sys_show_hide', '', 'danger', 'N', '0', 'admin', '2021-11-11 18:35:28', '', null, '隐藏菜单');
INSERT INTO `sys_dict_data` VALUES ('6', '1', '正常', '0', 'sys_normal_disable', '', 'primary', 'Y', '0', 'admin', '2021-11-11 18:35:28', '', null, '正常状态');
INSERT INTO `sys_dict_data` VALUES ('7', '2', '停用', '1', 'sys_normal_disable', '', 'danger', 'N', '0', 'admin', '2021-11-11 18:35:28', '', null, '停用状态');
INSERT INTO `sys_dict_data` VALUES ('8', '1', '正常', '0', 'sys_job_status', '', 'primary', 'Y', '0', 'admin', '2021-11-11 18:35:28', '', null, '正常状态');
INSERT INTO `sys_dict_data` VALUES ('9', '2', '暂停', '1', 'sys_job_status', '', 'danger', 'N', '0', 'admin', '2021-11-11 18:35:28', '', null, '停用状态');
INSERT INTO `sys_dict_data` VALUES ('10', '1', '默认', 'DEFAULT', 'sys_job_group', '', '', 'Y', '0', 'admin', '2021-11-11 18:35:28', '', null, '默认分组');
INSERT INTO `sys_dict_data` VALUES ('11', '2', '系统', 'SYSTEM', 'sys_job_group', '', '', 'N', '0', 'admin', '2021-11-11 18:35:28', '', null, '系统分组');
INSERT INTO `sys_dict_data` VALUES ('12', '1', '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', '0', 'admin', '2021-11-11 18:35:28', '', null, '系统默认是');
INSERT INTO `sys_dict_data` VALUES ('13', '2', '否', 'N', 'sys_yes_no', '', 'danger', 'N', '0', 'admin', '2021-11-11 18:35:28', '', null, '系统默认否');
INSERT INTO `sys_dict_data` VALUES ('14', '1', '通知', '1', 'sys_notice_type', '', 'warning', 'Y', '0', 'admin', '2021-11-11 18:35:28', '', null, '通知');
INSERT INTO `sys_dict_data` VALUES ('15', '2', '公告', '2', 'sys_notice_type', '', 'success', 'N', '0', 'admin', '2021-11-11 18:35:28', '', null, '公告');
INSERT INTO `sys_dict_data` VALUES ('16', '1', '正常', '0', 'sys_notice_status', '', 'primary', 'Y', '0', 'admin', '2021-11-11 18:35:28', '', null, '正常状态');
INSERT INTO `sys_dict_data` VALUES ('17', '2', '关闭', '1', 'sys_notice_status', '', 'danger', 'N', '0', 'admin', '2021-11-11 18:35:28', '', null, '关闭状态');
INSERT INTO `sys_dict_data` VALUES ('18', '1', '新增', '1', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2021-11-11 18:35:28', '', null, '新增操作');
INSERT INTO `sys_dict_data` VALUES ('19', '2', '修改', '2', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2021-11-11 18:35:28', '', null, '修改操作');
INSERT INTO `sys_dict_data` VALUES ('20', '3', '删除', '3', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2021-11-11 18:35:28', '', null, '删除操作');
INSERT INTO `sys_dict_data` VALUES ('21', '4', '授权', '4', 'sys_oper_type', '', 'primary', 'N', '0', 'admin', '2021-11-11 18:35:28', '', null, '授权操作');
INSERT INTO `sys_dict_data` VALUES ('22', '5', '导出', '5', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2021-11-11 18:35:28', '', null, '导出操作');
INSERT INTO `sys_dict_data` VALUES ('23', '6', '导入', '6', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2021-11-11 18:35:28', '', null, '导入操作');
INSERT INTO `sys_dict_data` VALUES ('24', '7', '强退', '7', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2021-11-11 18:35:28', '', null, '强退操作');
INSERT INTO `sys_dict_data` VALUES ('25', '8', '生成代码', '8', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2021-11-11 18:35:28', '', null, '生成操作');
INSERT INTO `sys_dict_data` VALUES ('26', '9', '清空数据', '9', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2021-11-11 18:35:28', '', null, '清空操作');
INSERT INTO `sys_dict_data` VALUES ('27', '1', '成功', '0', 'sys_common_status', '', 'primary', 'N', '0', 'admin', '2021-11-11 18:35:28', '', null, '正常状态');
INSERT INTO `sys_dict_data` VALUES ('28', '2', '失败', '1', 'sys_common_status', '', 'danger', 'N', '0', 'admin', '2021-11-11 18:35:28', '', null, '停用状态');

-- ----------------------------
-- Table structure for `sys_dict_type`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type` (
  `dict_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`),
  UNIQUE KEY `dict_type` (`dict_type`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='字典类型表';

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES ('1', '用户性别', 'sys_user_sex', '0', 'admin', '2021-11-11 18:35:27', '', null, '用户性别列表');
INSERT INTO `sys_dict_type` VALUES ('2', '菜单状态', 'sys_show_hide', '0', 'admin', '2021-11-11 18:35:27', '', null, '菜单状态列表');
INSERT INTO `sys_dict_type` VALUES ('3', '系统开关', 'sys_normal_disable', '0', 'admin', '2021-11-11 18:35:27', '', null, '系统开关列表');
INSERT INTO `sys_dict_type` VALUES ('4', '任务状态', 'sys_job_status', '0', 'admin', '2021-11-11 18:35:28', '', null, '任务状态列表');
INSERT INTO `sys_dict_type` VALUES ('5', '任务分组', 'sys_job_group', '0', 'admin', '2021-11-11 18:35:28', '', null, '任务分组列表');
INSERT INTO `sys_dict_type` VALUES ('6', '系统是否', 'sys_yes_no', '0', 'admin', '2021-11-11 18:35:28', '', null, '系统是否列表');
INSERT INTO `sys_dict_type` VALUES ('7', '通知类型', 'sys_notice_type', '0', 'admin', '2021-11-11 18:35:28', '', null, '通知类型列表');
INSERT INTO `sys_dict_type` VALUES ('8', '通知状态', 'sys_notice_status', '0', 'admin', '2021-11-11 18:35:28', '', null, '通知状态列表');
INSERT INTO `sys_dict_type` VALUES ('9', '操作类型', 'sys_oper_type', '0', 'admin', '2021-11-11 18:35:28', '', null, '操作类型列表');
INSERT INTO `sys_dict_type` VALUES ('10', '系统状态', 'sys_common_status', '0', 'admin', '2021-11-11 18:35:28', '', null, '登录状态列表');

-- ----------------------------
-- Table structure for `sys_job`
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(64) NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`job_id`,`job_name`,`job_group`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='定时任务调度表';

-- ----------------------------
-- Records of sys_job
-- ----------------------------
INSERT INTO `sys_job` VALUES ('1', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '0/10 * * * * ?', '3', '1', '1', 'admin', '2021-11-11 18:35:28', '', null, '');
INSERT INTO `sys_job` VALUES ('2', '系统默认（有参）', 'DEFAULT', 'ryTask.ryParams(\'ry\')', '0/15 * * * * ?', '3', '1', '1', 'admin', '2021-11-11 18:35:28', '', null, '');
INSERT INTO `sys_job` VALUES ('3', '系统默认（多参）', 'DEFAULT', 'ryTask.ryMultipleParams(\'ry\', true, 2000L, 316.50D, 100)', '0/20 * * * * ?', '3', '1', '1', 'admin', '2021-11-11 18:35:28', '', null, '');

-- ----------------------------
-- Table structure for `sys_job_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_job_log`;
CREATE TABLE `sys_job_log` (
  `job_log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_name` varchar(64) NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) DEFAULT NULL COMMENT '日志信息',
  `status` char(1) DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) DEFAULT '' COMMENT '异常信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务调度日志表';

-- ----------------------------
-- Records of sys_job_log
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_logininfor`
-- ----------------------------
DROP TABLE IF EXISTS `sys_logininfor`;
CREATE TABLE `sys_logininfor` (
  `info_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `user_name` varchar(50) DEFAULT '' COMMENT '用户账号',
  `ipaddr` varchar(128) DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) DEFAULT '' COMMENT '操作系统',
  `status` char(1) DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) DEFAULT '' COMMENT '提示消息',
  `login_time` datetime DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`)
) ENGINE=InnoDB AUTO_INCREMENT=285 DEFAULT CHARSET=utf8 COMMENT='系统访问记录';

-- ----------------------------
-- Records of sys_logininfor
-- ----------------------------
INSERT INTO `sys_logininfor` VALUES ('100', 'admin', '127.0.0.1', '内网IP', 'Internet Explorer 11', 'Windows 10', '0', '登录成功', '2021-11-11 19:00:16');
INSERT INTO `sys_logininfor` VALUES ('101', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-11 19:04:33');
INSERT INTO `sys_logininfor` VALUES ('102', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-12 14:09:51');
INSERT INTO `sys_logininfor` VALUES ('103', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-12 15:51:11');
INSERT INTO `sys_logininfor` VALUES ('104', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '验证码错误', '2021-11-12 16:33:13');
INSERT INTO `sys_logininfor` VALUES ('105', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-12 16:33:20');
INSERT INTO `sys_logininfor` VALUES ('106', 'admin', '127.0.0.1', '内网IP', 'Internet Explorer 11', 'Windows 10', '0', '登录成功', '2021-11-12 19:06:09');
INSERT INTO `sys_logininfor` VALUES ('107', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-15 06:37:06');
INSERT INTO `sys_logininfor` VALUES ('108', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-15 06:38:02');
INSERT INTO `sys_logininfor` VALUES ('109', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-15 06:42:09');
INSERT INTO `sys_logininfor` VALUES ('110', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-15 06:46:23');
INSERT INTO `sys_logininfor` VALUES ('111', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-15 06:48:38');
INSERT INTO `sys_logininfor` VALUES ('112', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '退出成功', '2021-11-15 06:58:38');
INSERT INTO `sys_logininfor` VALUES ('113', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-15 06:58:45');
INSERT INTO `sys_logininfor` VALUES ('114', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-15 07:33:32');
INSERT INTO `sys_logininfor` VALUES ('115', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '验证码错误', '2021-11-15 08:32:41');
INSERT INTO `sys_logininfor` VALUES ('116', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-15 08:32:46');
INSERT INTO `sys_logininfor` VALUES ('117', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '验证码错误', '2021-11-15 09:35:57');
INSERT INTO `sys_logininfor` VALUES ('118', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-15 09:36:00');
INSERT INTO `sys_logininfor` VALUES ('119', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-15 09:36:45');
INSERT INTO `sys_logininfor` VALUES ('120', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-15 10:30:25');
INSERT INTO `sys_logininfor` VALUES ('121', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '退出成功', '2021-11-15 10:53:43');
INSERT INTO `sys_logininfor` VALUES ('122', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-15 10:53:57');
INSERT INTO `sys_logininfor` VALUES ('123', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '退出成功', '2021-11-15 10:56:14');
INSERT INTO `sys_logininfor` VALUES ('124', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '验证码错误', '2021-11-15 10:56:20');
INSERT INTO `sys_logininfor` VALUES ('125', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-15 10:56:36');
INSERT INTO `sys_logininfor` VALUES ('126', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-15 11:27:14');
INSERT INTO `sys_logininfor` VALUES ('127', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-15 14:54:02');
INSERT INTO `sys_logininfor` VALUES ('128', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '验证码错误', '2021-11-15 15:14:59');
INSERT INTO `sys_logininfor` VALUES ('129', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-15 15:15:04');
INSERT INTO `sys_logininfor` VALUES ('130', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-15 16:12:51');
INSERT INTO `sys_logininfor` VALUES ('131', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-15 16:13:20');
INSERT INTO `sys_logininfor` VALUES ('132', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-15 17:32:17');
INSERT INTO `sys_logininfor` VALUES ('133', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-15 18:12:11');
INSERT INTO `sys_logininfor` VALUES ('134', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-15 18:48:39');
INSERT INTO `sys_logininfor` VALUES ('135', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-16 09:36:59');
INSERT INTO `sys_logininfor` VALUES ('136', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-16 16:04:40');
INSERT INTO `sys_logininfor` VALUES ('137', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-16 16:36:40');
INSERT INTO `sys_logininfor` VALUES ('138', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-17 15:17:12');
INSERT INTO `sys_logininfor` VALUES ('139', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-18 08:50:02');
INSERT INTO `sys_logininfor` VALUES ('140', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-18 14:08:00');
INSERT INTO `sys_logininfor` VALUES ('141', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-18 15:37:26');
INSERT INTO `sys_logininfor` VALUES ('142', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-18 16:52:22');
INSERT INTO `sys_logininfor` VALUES ('143', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-19 09:12:31');
INSERT INTO `sys_logininfor` VALUES ('144', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '验证码错误', '2021-11-19 11:31:54');
INSERT INTO `sys_logininfor` VALUES ('145', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-19 11:31:59');
INSERT INTO `sys_logininfor` VALUES ('146', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-19 15:24:10');
INSERT INTO `sys_logininfor` VALUES ('147', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-19 16:34:34');
INSERT INTO `sys_logininfor` VALUES ('148', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '验证码错误', '2021-11-22 09:42:20');
INSERT INTO `sys_logininfor` VALUES ('149', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '验证码错误', '2021-11-22 09:42:25');
INSERT INTO `sys_logininfor` VALUES ('150', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-22 09:42:31');
INSERT INTO `sys_logininfor` VALUES ('151', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-22 15:31:38');
INSERT INTO `sys_logininfor` VALUES ('152', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-22 20:39:15');
INSERT INTO `sys_logininfor` VALUES ('153', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-23 08:23:52');
INSERT INTO `sys_logininfor` VALUES ('154', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-23 14:30:31');
INSERT INTO `sys_logininfor` VALUES ('155', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-23 16:34:17');
INSERT INTO `sys_logininfor` VALUES ('156', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-23 20:56:26');
INSERT INTO `sys_logininfor` VALUES ('157', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '验证码错误', '2021-11-24 15:20:24');
INSERT INTO `sys_logininfor` VALUES ('158', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-24 15:20:31');
INSERT INTO `sys_logininfor` VALUES ('159', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-25 09:57:19');
INSERT INTO `sys_logininfor` VALUES ('160', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '验证码错误', '2021-11-25 11:13:38');
INSERT INTO `sys_logininfor` VALUES ('161', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-25 11:13:45');
INSERT INTO `sys_logininfor` VALUES ('162', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '验证码错误', '2021-11-25 14:14:45');
INSERT INTO `sys_logininfor` VALUES ('163', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-25 14:14:54');
INSERT INTO `sys_logininfor` VALUES ('164', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-25 16:09:34');
INSERT INTO `sys_logininfor` VALUES ('165', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-26 11:12:29');
INSERT INTO `sys_logininfor` VALUES ('166', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-26 11:30:47');
INSERT INTO `sys_logininfor` VALUES ('167', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-26 14:11:25');
INSERT INTO `sys_logininfor` VALUES ('168', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '验证码错误', '2021-11-26 15:02:42');
INSERT INTO `sys_logininfor` VALUES ('169', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-26 15:02:48');
INSERT INTO `sys_logininfor` VALUES ('170', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-26 18:13:44');
INSERT INTO `sys_logininfor` VALUES ('171', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-27 10:00:00');
INSERT INTO `sys_logininfor` VALUES ('172', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-27 15:00:32');
INSERT INTO `sys_logininfor` VALUES ('173', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '验证码错误', '2021-11-27 16:31:39');
INSERT INTO `sys_logininfor` VALUES ('174', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '验证码错误', '2021-11-27 16:31:56');
INSERT INTO `sys_logininfor` VALUES ('175', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '验证码错误', '2021-11-27 16:31:59');
INSERT INTO `sys_logininfor` VALUES ('176', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-27 16:32:07');
INSERT INTO `sys_logininfor` VALUES ('177', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-27 18:29:22');
INSERT INTO `sys_logininfor` VALUES ('178', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '验证码错误', '2021-11-28 11:29:31');
INSERT INTO `sys_logininfor` VALUES ('179', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-28 11:29:41');
INSERT INTO `sys_logininfor` VALUES ('180', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-29 17:48:48');
INSERT INTO `sys_logininfor` VALUES ('181', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-30 09:34:27');
INSERT INTO `sys_logininfor` VALUES ('182', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '退出成功', '2021-11-30 10:45:44');
INSERT INTO `sys_logininfor` VALUES ('183', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-30 10:45:52');
INSERT INTO `sys_logininfor` VALUES ('184', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-30 14:32:50');
INSERT INTO `sys_logininfor` VALUES ('185', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-30 16:38:39');
INSERT INTO `sys_logininfor` VALUES ('186', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '退出成功', '2021-11-30 17:00:58');
INSERT INTO `sys_logininfor` VALUES ('187', 'fanhuibin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-30 17:01:15');
INSERT INTO `sys_logininfor` VALUES ('188', 'fanhuibin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '退出成功', '2021-11-30 17:56:43');
INSERT INTO `sys_logininfor` VALUES ('189', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-30 17:56:50');
INSERT INTO `sys_logininfor` VALUES ('190', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '退出成功', '2021-11-30 18:17:00');
INSERT INTO `sys_logininfor` VALUES ('191', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '用户不存在/密码错误', '2021-11-30 18:17:10');
INSERT INTO `sys_logininfor` VALUES ('192', 'fanhuibin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-30 18:17:23');
INSERT INTO `sys_logininfor` VALUES ('193', 'fanhuibin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '退出成功', '2021-11-30 18:20:53');
INSERT INTO `sys_logininfor` VALUES ('194', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-30 18:21:01');
INSERT INTO `sys_logininfor` VALUES ('195', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '退出成功', '2021-11-30 18:41:49');
INSERT INTO `sys_logininfor` VALUES ('196', 'fanhuibin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-30 18:42:05');
INSERT INTO `sys_logininfor` VALUES ('197', 'fanhuibin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '退出成功', '2021-11-30 18:46:56');
INSERT INTO `sys_logininfor` VALUES ('198', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '验证码错误', '2021-11-30 18:47:06');
INSERT INTO `sys_logininfor` VALUES ('199', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '验证码错误', '2021-11-30 18:47:13');
INSERT INTO `sys_logininfor` VALUES ('200', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-11-30 18:47:19');
INSERT INTO `sys_logininfor` VALUES ('201', 'fanhuibin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-01 08:38:16');
INSERT INTO `sys_logininfor` VALUES ('202', 'fanhuibin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-01 08:38:56');
INSERT INTO `sys_logininfor` VALUES ('203', 'fanhuibin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '退出成功', '2021-12-01 08:48:49');
INSERT INTO `sys_logininfor` VALUES ('204', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-01 08:48:58');
INSERT INTO `sys_logininfor` VALUES ('205', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '退出成功', '2021-12-01 08:49:14');
INSERT INTO `sys_logininfor` VALUES ('206', 'fanhuibin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-01 08:49:32');
INSERT INTO `sys_logininfor` VALUES ('207', 'fanhuibin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '退出成功', '2021-12-01 08:50:14');
INSERT INTO `sys_logininfor` VALUES ('208', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-01 08:50:28');
INSERT INTO `sys_logininfor` VALUES ('209', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '退出成功', '2021-12-01 08:52:35');
INSERT INTO `sys_logininfor` VALUES ('210', 'fanhuibin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-01 08:52:48');
INSERT INTO `sys_logininfor` VALUES ('211', 'fanhuibin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-01 09:54:57');
INSERT INTO `sys_logininfor` VALUES ('212', 'fanhuibin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '退出成功', '2021-12-01 10:21:40');
INSERT INTO `sys_logininfor` VALUES ('213', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '验证码错误', '2021-12-01 10:21:56');
INSERT INTO `sys_logininfor` VALUES ('214', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-01 10:22:05');
INSERT INTO `sys_logininfor` VALUES ('215', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '退出成功', '2021-12-01 10:24:40');
INSERT INTO `sys_logininfor` VALUES ('216', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-01 10:24:57');
INSERT INTO `sys_logininfor` VALUES ('217', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '退出成功', '2021-12-01 10:59:34');
INSERT INTO `sys_logininfor` VALUES ('218', 'fanhuibin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-01 10:59:56');
INSERT INTO `sys_logininfor` VALUES ('219', 'fanhuibin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '退出成功', '2021-12-01 11:03:43');
INSERT INTO `sys_logininfor` VALUES ('220', 'fanhuibin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '验证码错误', '2021-12-01 11:03:57');
INSERT INTO `sys_logininfor` VALUES ('221', 'fanhuibin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-01 11:04:08');
INSERT INTO `sys_logininfor` VALUES ('222', 'fanhuibin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '退出成功', '2021-12-01 11:04:25');
INSERT INTO `sys_logininfor` VALUES ('223', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '验证码错误', '2021-12-01 11:04:34');
INSERT INTO `sys_logininfor` VALUES ('224', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-01 11:04:39');
INSERT INTO `sys_logininfor` VALUES ('225', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '退出成功', '2021-12-01 11:06:57');
INSERT INTO `sys_logininfor` VALUES ('226', 'fanhuibin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-01 11:07:10');
INSERT INTO `sys_logininfor` VALUES ('227', 'fanhuibin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '退出成功', '2021-12-01 11:08:34');
INSERT INTO `sys_logininfor` VALUES ('228', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '验证码错误', '2021-12-01 11:08:48');
INSERT INTO `sys_logininfor` VALUES ('229', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-01 11:08:56');
INSERT INTO `sys_logininfor` VALUES ('230', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '退出成功', '2021-12-01 11:20:13');
INSERT INTO `sys_logininfor` VALUES ('231', 'finance', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-01 11:20:25');
INSERT INTO `sys_logininfor` VALUES ('232', 'finance', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '退出成功', '2021-12-01 11:20:50');
INSERT INTO `sys_logininfor` VALUES ('233', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-01 11:22:33');
INSERT INTO `sys_logininfor` VALUES ('234', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '退出成功', '2021-12-01 11:25:44');
INSERT INTO `sys_logininfor` VALUES ('235', 'manager', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-01 11:25:56');
INSERT INTO `sys_logininfor` VALUES ('236', 'manager', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '退出成功', '2021-12-01 11:27:40');
INSERT INTO `sys_logininfor` VALUES ('237', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-01 11:28:00');
INSERT INTO `sys_logininfor` VALUES ('238', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '退出成功', '2021-12-01 11:31:42');
INSERT INTO `sys_logininfor` VALUES ('239', 'manager', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-01 11:31:55');
INSERT INTO `sys_logininfor` VALUES ('240', 'manager', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '退出成功', '2021-12-01 11:39:28');
INSERT INTO `sys_logininfor` VALUES ('241', 'manager', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-01 11:39:42');
INSERT INTO `sys_logininfor` VALUES ('242', 'manager', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '退出成功', '2021-12-01 11:49:43');
INSERT INTO `sys_logininfor` VALUES ('243', 'manager', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-01 14:15:46');
INSERT INTO `sys_logininfor` VALUES ('244', 'manager', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '退出成功', '2021-12-01 14:22:00');
INSERT INTO `sys_logininfor` VALUES ('245', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-01 15:08:52');
INSERT INTO `sys_logininfor` VALUES ('246', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '退出成功', '2021-12-01 15:09:10');
INSERT INTO `sys_logininfor` VALUES ('247', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '验证码已失效', '2021-12-01 15:51:46');
INSERT INTO `sys_logininfor` VALUES ('248', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '用户不存在/密码错误', '2021-12-01 15:51:51');
INSERT INTO `sys_logininfor` VALUES ('249', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '验证码错误', '2021-12-01 15:51:55');
INSERT INTO `sys_logininfor` VALUES ('250', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '用户不存在/密码错误', '2021-12-01 15:52:01');
INSERT INTO `sys_logininfor` VALUES ('251', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '验证码错误', '2021-12-01 15:52:08');
INSERT INTO `sys_logininfor` VALUES ('252', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '验证码错误', '2021-12-01 15:52:14');
INSERT INTO `sys_logininfor` VALUES ('253', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '用户不存在/密码错误', '2021-12-01 15:52:23');
INSERT INTO `sys_logininfor` VALUES ('254', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-01 15:52:38');
INSERT INTO `sys_logininfor` VALUES ('255', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '退出成功', '2021-12-01 16:08:44');
INSERT INTO `sys_logininfor` VALUES ('256', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '验证码已失效', '2021-12-02 18:28:53');
INSERT INTO `sys_logininfor` VALUES ('257', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-02 18:29:01');
INSERT INTO `sys_logininfor` VALUES ('258', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-03 09:05:03');
INSERT INTO `sys_logininfor` VALUES ('259', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-03 11:33:31');
INSERT INTO `sys_logininfor` VALUES ('260', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-07 20:53:07');
INSERT INTO `sys_logininfor` VALUES ('261', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-08 07:36:56');
INSERT INTO `sys_logininfor` VALUES ('262', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '验证码错误', '2021-12-08 09:24:29');
INSERT INTO `sys_logininfor` VALUES ('263', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-08 09:24:35');
INSERT INTO `sys_logininfor` VALUES ('264', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-08 11:24:33');
INSERT INTO `sys_logininfor` VALUES ('265', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-08 11:47:33');
INSERT INTO `sys_logininfor` VALUES ('266', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-08 15:47:45');
INSERT INTO `sys_logininfor` VALUES ('267', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-08 19:28:48');
INSERT INTO `sys_logininfor` VALUES ('268', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-09 07:35:47');
INSERT INTO `sys_logininfor` VALUES ('269', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-18 06:59:33');
INSERT INTO `sys_logininfor` VALUES ('270', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-18 14:09:11');
INSERT INTO `sys_logininfor` VALUES ('271', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '验证码错误', '2021-12-21 09:52:37');
INSERT INTO `sys_logininfor` VALUES ('272', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-21 09:52:43');
INSERT INTO `sys_logininfor` VALUES ('273', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-10-21 10:40:18');
INSERT INTO `sys_logininfor` VALUES ('274', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-21 15:15:38');
INSERT INTO `sys_logininfor` VALUES ('275', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-21 17:48:14');
INSERT INTO `sys_logininfor` VALUES ('276', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-09 07:07:44');
INSERT INTO `sys_logininfor` VALUES ('277', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-09 07:09:37');
INSERT INTO `sys_logininfor` VALUES ('278', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '验证码错误', '2021-12-09 11:07:42');
INSERT INTO `sys_logininfor` VALUES ('279', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '验证码错误', '2021-12-09 11:07:49');
INSERT INTO `sys_logininfor` VALUES ('280', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-09 11:07:55');
INSERT INTO `sys_logininfor` VALUES ('281', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-10 13:20:37');
INSERT INTO `sys_logininfor` VALUES ('282', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '验证码错误', '2021-12-11 11:28:41');
INSERT INTO `sys_logininfor` VALUES ('283', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-11 11:28:54');
INSERT INTO `sys_logininfor` VALUES ('284', 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2021-12-11 11:57:35');

-- ----------------------------
-- Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `path` varchar(200) DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) DEFAULT NULL COMMENT '组件路径',
  `query` varchar(255) DEFAULT NULL COMMENT '路由参数',
  `is_frame` int(1) DEFAULT '1' COMMENT '是否为外链（0是 1否）',
  `is_cache` int(1) DEFAULT '0' COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2023 DEFAULT CHARSET=utf8 COMMENT='菜单权限表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '系统管理', '0', '10', 'system', null, '', '1', '0', 'M', '0', '0', '', 'system', 'admin', '2021-11-11 18:35:24', 'admin', '2021-11-12 16:45:01', '系统管理目录');
INSERT INTO `sys_menu` VALUES ('2', '系统监控', '0', '20', 'monitor', null, '', '1', '0', 'M', '0', '0', '', 'monitor', 'admin', '2021-11-11 18:35:24', 'admin', '2021-11-12 16:45:09', '系统监控目录');
INSERT INTO `sys_menu` VALUES ('3', '系统工具', '0', '30', 'tool', null, '', '1', '0', 'M', '0', '0', '', 'tool', 'admin', '2021-11-11 18:35:24', 'admin', '2021-11-12 16:45:13', '系统工具目录');
INSERT INTO `sys_menu` VALUES ('4', '肇新智慧物业', '0', '40', 'http://zhaoxinms.com:81', null, '', '0', '0', 'M', '0', '0', '', 'guide', 'admin', '2021-11-11 18:35:24', 'admin', '2021-12-09 11:57:28', '若依官网地址');
INSERT INTO `sys_menu` VALUES ('100', '用户管理', '1', '1', 'user', 'system/user/index', '', '1', '0', 'C', '0', '0', 'system:user:list', 'user', 'admin', '2021-11-11 18:35:24', '', null, '用户管理菜单');
INSERT INTO `sys_menu` VALUES ('101', '角色管理', '1', '2', 'role', 'system/role/index', '', '1', '0', 'C', '0', '0', 'system:role:list', 'peoples', 'admin', '2021-11-11 18:35:24', '', null, '角色管理菜单');
INSERT INTO `sys_menu` VALUES ('102', '菜单管理', '1', '3', 'menu', 'system/menu/index', '', '1', '0', 'C', '0', '0', 'system:menu:list', 'tree-table', 'admin', '2021-11-11 18:35:24', '', null, '菜单管理菜单');
INSERT INTO `sys_menu` VALUES ('103', '部门管理', '1', '4', 'dept', 'system/dept/index', '', '1', '0', 'C', '0', '0', 'system:dept:list', 'tree', 'admin', '2021-11-11 18:35:24', '', null, '部门管理菜单');
INSERT INTO `sys_menu` VALUES ('104', '岗位管理', '1', '5', 'post', 'system/post/index', '', '1', '0', 'C', '0', '0', 'system:post:list', 'post', 'admin', '2021-11-11 18:35:24', '', null, '岗位管理菜单');
INSERT INTO `sys_menu` VALUES ('105', '字典管理', '1', '6', 'dict', 'system/dict/index', '', '1', '0', 'C', '0', '0', 'system:dict:list', 'dict', 'admin', '2021-11-11 18:35:24', '', null, '字典管理菜单');
INSERT INTO `sys_menu` VALUES ('106', '参数设置', '1', '7', 'config', 'system/config/index', '', '1', '0', 'C', '0', '0', 'system:config:list', 'edit', 'admin', '2021-11-11 18:35:24', '', null, '参数设置菜单');
INSERT INTO `sys_menu` VALUES ('107', '通知公告', '1', '8', 'notice', 'system/notice/index', '', '1', '0', 'C', '0', '0', 'system:notice:list', 'message', 'admin', '2021-11-11 18:35:24', '', null, '通知公告菜单');
INSERT INTO `sys_menu` VALUES ('108', '日志管理', '1', '9', 'log', '', '', '1', '0', 'M', '0', '0', '', 'log', 'admin', '2021-11-11 18:35:24', '', null, '日志管理菜单');
INSERT INTO `sys_menu` VALUES ('109', '在线用户', '2', '1', 'online', 'monitor/online/index', '', '1', '0', 'C', '0', '0', 'monitor:online:list', 'online', 'admin', '2021-11-11 18:35:24', '', null, '在线用户菜单');
INSERT INTO `sys_menu` VALUES ('110', '定时任务', '2', '2', 'job', 'monitor/job/index', '', '1', '0', 'C', '0', '0', 'monitor:job:list', 'job', 'admin', '2021-11-11 18:35:24', '', null, '定时任务菜单');
INSERT INTO `sys_menu` VALUES ('111', '数据监控', '2', '3', 'druid', 'monitor/druid/index', '', '1', '0', 'C', '0', '0', 'monitor:druid:list', 'druid', 'admin', '2021-11-11 18:35:24', '', null, '数据监控菜单');
INSERT INTO `sys_menu` VALUES ('112', '服务监控', '2', '4', 'server', 'monitor/server/index', '', '1', '0', 'C', '0', '0', 'monitor:server:list', 'server', 'admin', '2021-11-11 18:35:24', '', null, '服务监控菜单');
INSERT INTO `sys_menu` VALUES ('113', '缓存监控', '2', '5', 'cache', 'monitor/cache/index', '', '1', '0', 'C', '0', '0', 'monitor:cache:list', 'redis', 'admin', '2021-11-11 18:35:24', '', null, '缓存监控菜单');
INSERT INTO `sys_menu` VALUES ('114', '表单构建', '3', '1', 'build', 'tool/build/index', '', '1', '0', 'C', '0', '0', 'tool:build:list', 'build', 'admin', '2021-11-11 18:35:25', '', null, '表单构建菜单');
INSERT INTO `sys_menu` VALUES ('115', '代码生成', '3', '2', 'gen', 'tool/gen/index', '', '1', '0', 'C', '0', '0', 'tool:gen:list', 'code', 'admin', '2021-11-11 18:35:25', '', null, '代码生成菜单');
INSERT INTO `sys_menu` VALUES ('116', '系统接口', '3', '3', 'swagger', 'tool/swagger/index', '', '1', '0', 'C', '0', '0', 'tool:swagger:list', 'swagger', 'admin', '2021-11-11 18:35:25', '', null, '系统接口菜单');
INSERT INTO `sys_menu` VALUES ('500', '操作日志', '108', '1', 'operlog', 'monitor/operlog/index', '', '1', '0', 'C', '0', '0', 'monitor:operlog:list', 'form', 'admin', '2021-11-11 18:35:25', '', null, '操作日志菜单');
INSERT INTO `sys_menu` VALUES ('501', '登录日志', '108', '2', 'logininfor', 'monitor/logininfor/index', '', '1', '0', 'C', '0', '0', 'monitor:logininfor:list', 'logininfor', 'admin', '2021-11-11 18:35:25', '', null, '登录日志菜单');
INSERT INTO `sys_menu` VALUES ('1001', '用户查询', '100', '1', '', '', '', '1', '0', 'F', '0', '0', 'system:user:query', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1002', '用户新增', '100', '2', '', '', '', '1', '0', 'F', '0', '0', 'system:user:add', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1003', '用户修改', '100', '3', '', '', '', '1', '0', 'F', '0', '0', 'system:user:edit', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1004', '用户删除', '100', '4', '', '', '', '1', '0', 'F', '0', '0', 'system:user:remove', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1005', '用户导出', '100', '5', '', '', '', '1', '0', 'F', '0', '0', 'system:user:export', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1006', '用户导入', '100', '6', '', '', '', '1', '0', 'F', '0', '0', 'system:user:import', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1007', '重置密码', '100', '7', '', '', '', '1', '0', 'F', '0', '0', 'system:user:resetPwd', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1008', '角色查询', '101', '1', '', '', '', '1', '0', 'F', '0', '0', 'system:role:query', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1009', '角色新增', '101', '2', '', '', '', '1', '0', 'F', '0', '0', 'system:role:add', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1010', '角色修改', '101', '3', '', '', '', '1', '0', 'F', '0', '0', 'system:role:edit', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1011', '角色删除', '101', '4', '', '', '', '1', '0', 'F', '0', '0', 'system:role:remove', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1012', '角色导出', '101', '5', '', '', '', '1', '0', 'F', '0', '0', 'system:role:export', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1013', '菜单查询', '102', '1', '', '', '', '1', '0', 'F', '0', '0', 'system:menu:query', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1014', '菜单新增', '102', '2', '', '', '', '1', '0', 'F', '0', '0', 'system:menu:add', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1015', '菜单修改', '102', '3', '', '', '', '1', '0', 'F', '0', '0', 'system:menu:edit', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1016', '菜单删除', '102', '4', '', '', '', '1', '0', 'F', '0', '0', 'system:menu:remove', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1017', '部门查询', '103', '1', '', '', '', '1', '0', 'F', '0', '0', 'system:dept:query', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1018', '部门新增', '103', '2', '', '', '', '1', '0', 'F', '0', '0', 'system:dept:add', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1019', '部门修改', '103', '3', '', '', '', '1', '0', 'F', '0', '0', 'system:dept:edit', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1020', '部门删除', '103', '4', '', '', '', '1', '0', 'F', '0', '0', 'system:dept:remove', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1021', '岗位查询', '104', '1', '', '', '', '1', '0', 'F', '0', '0', 'system:post:query', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1022', '岗位新增', '104', '2', '', '', '', '1', '0', 'F', '0', '0', 'system:post:add', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1023', '岗位修改', '104', '3', '', '', '', '1', '0', 'F', '0', '0', 'system:post:edit', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1024', '岗位删除', '104', '4', '', '', '', '1', '0', 'F', '0', '0', 'system:post:remove', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1025', '岗位导出', '104', '5', '', '', '', '1', '0', 'F', '0', '0', 'system:post:export', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1026', '字典查询', '105', '1', '#', '', '', '1', '0', 'F', '0', '0', 'system:dict:query', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1027', '字典新增', '105', '2', '#', '', '', '1', '0', 'F', '0', '0', 'system:dict:add', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1028', '字典修改', '105', '3', '#', '', '', '1', '0', 'F', '0', '0', 'system:dict:edit', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1029', '字典删除', '105', '4', '#', '', '', '1', '0', 'F', '0', '0', 'system:dict:remove', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1030', '字典导出', '105', '5', '#', '', '', '1', '0', 'F', '0', '0', 'system:dict:export', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1031', '参数查询', '106', '1', '#', '', '', '1', '0', 'F', '0', '0', 'system:config:query', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1032', '参数新增', '106', '2', '#', '', '', '1', '0', 'F', '0', '0', 'system:config:add', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1033', '参数修改', '106', '3', '#', '', '', '1', '0', 'F', '0', '0', 'system:config:edit', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1034', '参数删除', '106', '4', '#', '', '', '1', '0', 'F', '0', '0', 'system:config:remove', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1035', '参数导出', '106', '5', '#', '', '', '1', '0', 'F', '0', '0', 'system:config:export', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1036', '公告查询', '107', '1', '#', '', '', '1', '0', 'F', '0', '0', 'system:notice:query', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1037', '公告新增', '107', '2', '#', '', '', '1', '0', 'F', '0', '0', 'system:notice:add', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1038', '公告修改', '107', '3', '#', '', '', '1', '0', 'F', '0', '0', 'system:notice:edit', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1039', '公告删除', '107', '4', '#', '', '', '1', '0', 'F', '0', '0', 'system:notice:remove', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1040', '操作查询', '500', '1', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:operlog:query', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1041', '操作删除', '500', '2', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:operlog:remove', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1042', '日志导出', '500', '4', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:operlog:export', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1043', '登录查询', '501', '1', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:logininfor:query', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1044', '登录删除', '501', '2', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:logininfor:remove', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1045', '日志导出', '501', '3', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:logininfor:export', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1046', '在线查询', '109', '1', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:online:query', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1047', '批量强退', '109', '2', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:online:batchLogout', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1048', '单条强退', '109', '3', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:online:forceLogout', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1049', '任务查询', '110', '1', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:job:query', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1050', '任务新增', '110', '2', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:job:add', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1051', '任务修改', '110', '3', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:job:edit', '#', 'admin', '2021-11-11 18:35:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('1052', '任务删除', '110', '4', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:job:remove', '#', 'admin', '2021-11-11 18:35:26', '', null, '');
INSERT INTO `sys_menu` VALUES ('1053', '状态修改', '110', '5', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:job:changeStatus', '#', 'admin', '2021-11-11 18:35:26', '', null, '');
INSERT INTO `sys_menu` VALUES ('1054', '任务导出', '110', '7', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:job:export', '#', 'admin', '2021-11-11 18:35:26', '', null, '');
INSERT INTO `sys_menu` VALUES ('1055', '生成查询', '115', '1', '#', '', '', '1', '0', 'F', '0', '0', 'tool:gen:query', '#', 'admin', '2021-11-11 18:35:26', '', null, '');
INSERT INTO `sys_menu` VALUES ('1056', '生成修改', '115', '2', '#', '', '', '1', '0', 'F', '0', '0', 'tool:gen:edit', '#', 'admin', '2021-11-11 18:35:26', '', null, '');
INSERT INTO `sys_menu` VALUES ('1057', '生成删除', '115', '3', '#', '', '', '1', '0', 'F', '0', '0', 'tool:gen:remove', '#', 'admin', '2021-11-11 18:35:26', '', null, '');
INSERT INTO `sys_menu` VALUES ('1058', '导入代码', '115', '2', '#', '', '', '1', '0', 'F', '0', '0', 'tool:gen:import', '#', 'admin', '2021-11-11 18:35:26', '', null, '');
INSERT INTO `sys_menu` VALUES ('1059', '预览代码', '115', '4', '#', '', '', '1', '0', 'F', '0', '0', 'tool:gen:preview', '#', 'admin', '2021-11-11 18:35:26', '', null, '');
INSERT INTO `sys_menu` VALUES ('1060', '生成代码', '115', '5', '#', '', '', '1', '0', 'F', '0', '0', 'tool:gen:code', '#', 'admin', '2021-11-11 18:35:26', '', null, '');
INSERT INTO `sys_menu` VALUES ('2000', '物业收费', '0', '1', 'payment', null, null, '1', '0', 'M', '0', '0', '', 'shopping', 'admin', '2021-11-12 16:44:50', 'admin', '2021-12-07 21:00:56', '');
INSERT INTO `sys_menu` VALUES ('2001', '商铺综合收费', '2000', '1', 'housepay', 'pms/housepay', null, '1', '0', 'C', '0', '0', '', 'money', 'admin', '2021-11-12 17:03:23', 'admin', '2021-12-07 21:02:14', '');
INSERT INTO `sys_menu` VALUES ('2002', '基础数据管理', '0', '3', 'config', null, null, '1', '0', 'M', '0', '0', null, 'cascader', 'admin', '2021-11-12 17:10:01', '', null, '');
INSERT INTO `sys_menu` VALUES ('2003', '收费项管理', '2002', '1', 'configfeeitem', 'pms/configfeeitem', null, '1', '0', 'C', '0', '0', null, 'list', 'admin', '2021-11-12 17:11:22', '', null, '');
INSERT INTO `sys_menu` VALUES ('2004', '商业区管理', '2002', '2', 'pms/confighouseblock', 'pms/confighouseblock', null, '1', '0', 'C', '0', '0', '', 'example', 'admin', '2021-11-15 08:15:22', 'admin', '2021-12-08 09:35:02', '');
INSERT INTO `sys_menu` VALUES ('2005', '商铺管理', '2002', '3', 'config/house', 'pms/confighouse', null, '1', '0', 'C', '0', '0', null, 'component', 'admin', '2021-11-15 08:27:15', '', null, '');
INSERT INTO `sys_menu` VALUES ('2006', '商铺租售', '2002', '4', 'config/contract', 'pms/confighousecontract', null, '1', '0', 'C', '0', '0', '', 'switch', 'admin', '2021-11-15 09:03:03', 'admin', '2021-11-15 09:06:14', '');
INSERT INTO `sys_menu` VALUES ('2007', '收费数据管理', '0', '2', 'fee', null, null, '1', '0', 'M', '0', '0', null, 'build', 'admin', '2021-11-15 11:57:45', '', null, '');
INSERT INTO `sys_menu` VALUES ('2008', '商铺收费数据', '2007', '2', 'bill', 'pms/paymentbill', null, '1', '0', 'C', '0', '0', '', 'edit', 'admin', '2021-11-15 11:59:55', 'admin', '2021-11-15 12:03:02', '');
INSERT INTO `sys_menu` VALUES ('2009', '抄表数据管理', '2007', '1', 'meter', 'pms/paymentmeter', null, '1', '0', 'C', '0', '0', null, 'time-range', 'admin', '2021-11-15 12:08:27', '', null, '');
INSERT INTO `sys_menu` VALUES ('2010', '临时收费', '2000', '2', 'temppay', 'pms/paymenttemp', null, '1', '0', 'C', '0', '0', '', 'form', 'admin', '2021-11-15 15:06:49', 'admin', '2021-12-07 21:05:46', '');
INSERT INTO `sys_menu` VALUES ('2011', '押金管理', '2000', '3', 'deposit', 'pms/paymentdeposit', null, '1', '0', 'C', '0', '0', '', 'dict', 'admin', '2021-11-15 15:08:42', 'admin', '2021-12-07 21:06:18', '');
INSERT INTO `sys_menu` VALUES ('2012', '预存款管理', '2000', '4', 'pre', 'pms/paymentpre', null, '1', '0', 'C', '0', '0', '', 'documentation', 'admin', '2021-11-15 15:10:23', 'admin', '2021-12-07 21:07:59', '');
INSERT INTO `sys_menu` VALUES ('2013', '统计报表', '0', '5', 'statistics', null, null, '1', '0', 'M', '0', '0', null, 'monitor', 'admin', '2021-11-18 14:12:12', '', null, '');
INSERT INTO `sys_menu` VALUES ('2014', '收费明细表', '2013', '2', 'payLog', 'statistics/paylog', null, '1', '0', 'C', '0', '0', 'statistics:paymentBill:overdue', 'date-range', 'admin', '2021-11-18 14:21:15', 'admin', '2021-11-30 16:45:19', '');
INSERT INTO `sys_menu` VALUES ('2015', '待生成的费用', '2013', '4', 'nextFee', 'statistics/nextFee', null, '1', '0', 'C', '0', '0', 'statistics:nextFee:nextFee', 'skill', 'admin', '2021-11-18 14:50:23', 'admin', '2021-11-30 16:51:26', '');
INSERT INTO `sys_menu` VALUES ('2016', '收费统计（商铺）', '2013', '9', 'userFee', 'statistics/payment/house', null, '1', '0', 'C', '0', '0', 'statistics:PaymentStatistics:house', 'chart', 'admin', '2021-11-18 14:52:00', 'admin', '2021-11-30 16:54:54', '');
INSERT INTO `sys_menu` VALUES ('2018', '收费统计', '2013', '8', 'fee', 'statistics/payment', null, '1', '0', 'C', '0', '0', 'statistics:PaymentStatistics:PaymentStatistics', 'chart', 'admin', '2021-11-18 15:05:20', 'admin', '2021-11-30 16:54:41', '');
INSERT INTO `sys_menu` VALUES ('2019', '预收款余额', '2013', '5', 'preAccount', 'statistics/preAccount', null, '1', '0', 'C', '0', '0', 'statistics:preAccount:list', 'druid', 'admin', '2021-11-18 15:11:00', 'admin', '2021-11-30 16:51:51', '');
INSERT INTO `sys_menu` VALUES ('2020', '收费日报表', '2013', '0', 'dailyReport', 'statistics/daily', null, '1', '0', 'C', '0', '0', 'statistics:dailyFee:dailyFee', 'job', 'admin', '2021-11-23 17:19:55', 'admin', '2021-11-30 16:44:47', '');
INSERT INTO `sys_menu` VALUES ('2021', '欠费数据', '2013', '9', 'overdue', 'statistics/overdue', null, '1', '0', 'C', '0', '0', 'statistics:paymentBill:overdue', 'server', 'admin', '2021-11-23 17:44:42', 'admin', '2021-08-18 09:46:47', '');
INSERT INTO `sys_menu` VALUES ('2022', '收费项汇总表', '2013', '1', 'dailyFee', 'statistics/dailyFee', null, '1', '0', 'C', '0', '0', 'statistics:dailyFeeReport:dailyFeeReport', 'excel', 'admin', '2021-11-23 17:47:28', 'admin', '2021-07-03 16:46:39', '');

-- ----------------------------
-- Table structure for `sys_notice`
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice` (
  `notice_id` int(4) NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(50) NOT NULL COMMENT '公告标题',
  `notice_type` char(1) NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` longblob COMMENT '公告内容',
  `status` char(1) DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='通知公告表';

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO `sys_notice` VALUES ('1', '温馨提醒：2018-07-01 若依新版本发布啦', '2', 0xE696B0E78988E69CACE58685E5AEB9, '0', 'admin', '2021-11-11 18:35:29', '', null, '管理员');
INSERT INTO `sys_notice` VALUES ('2', '维护通知：2018-07-01 若依系统凌晨维护', '1', 0xE7BBB4E68AA4E58685E5AEB9, '0', 'admin', '2021-11-11 18:35:29', '', null, '管理员');

-- ----------------------------
-- Table structure for `sys_oper_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log` (
  `oper_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) DEFAULT '' COMMENT '模块标题',
  `business_type` int(2) DEFAULT '0' COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(100) DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) DEFAULT '' COMMENT '请求方式',
  `operator_type` int(1) DEFAULT '0' COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(128) DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) DEFAULT '' COMMENT '返回参数',
  `status` int(1) DEFAULT '0' COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`oper_id`)
) ENGINE=InnoDB AUTO_INCREMENT=356 DEFAULT CHARSET=utf8 COMMENT='操作日志记录';

-- ----------------------------
-- Records of sys_oper_log
-- ----------------------------
INSERT INTO `sys_oper_log` VALUES ('100', '在线用户', '7', 'com.zhaoxinms.web.controller.monitor.SysUserOnlineController.forceLogout()', 'DELETE', '1', 'admin', null, '/monitor/online/c4e44416-1923-4fdd-8ce9-595cb1e4073a', '127.0.0.1', '内网IP', '{tokenId=c4e44416-1923-4fdd-8ce9-595cb1e4073a}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-11 19:05:57');
INSERT INTO `sys_oper_log` VALUES ('101', '菜单管理', '1', 'com.zhaoxinms.web.controller.system.SysMenuController.add()', 'POST', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"shopping\",\"orderNum\":\"1\",\"menuName\":\"日常收费\",\"params\":{},\"parentId\":0,\"isCache\":\"0\",\"path\":\"payment\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"M\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-12 16:44:50');
INSERT INTO `sys_oper_log` VALUES ('102', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"query\":\"\",\"icon\":\"system\",\"orderNum\":\"10\",\"menuName\":\"系统管理\",\"params\":{},\"parentId\":0,\"isCache\":\"0\",\"path\":\"system\",\"children\":[],\"createTime\":1636626924000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":1,\"menuType\":\"M\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-12 16:45:01');
INSERT INTO `sys_oper_log` VALUES ('103', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"query\":\"\",\"icon\":\"monitor\",\"orderNum\":\"20\",\"menuName\":\"系统监控\",\"params\":{},\"parentId\":0,\"isCache\":\"0\",\"path\":\"monitor\",\"children\":[],\"createTime\":1636626924000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2,\"menuType\":\"M\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-12 16:45:09');
INSERT INTO `sys_oper_log` VALUES ('104', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"query\":\"\",\"icon\":\"tool\",\"orderNum\":\"30\",\"menuName\":\"系统工具\",\"params\":{},\"parentId\":0,\"isCache\":\"0\",\"path\":\"tool\",\"children\":[],\"createTime\":1636626924000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":3,\"menuType\":\"M\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-12 16:45:13');
INSERT INTO `sys_oper_log` VALUES ('105', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"query\":\"\",\"icon\":\"guide\",\"orderNum\":\"40\",\"menuName\":\"若依官网\",\"params\":{},\"parentId\":0,\"isCache\":\"0\",\"path\":\"http://ruoyi.vip\",\"children\":[],\"createTime\":1636626924000,\"updateBy\":\"admin\",\"isFrame\":\"0\",\"menuId\":4,\"menuType\":\"M\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-12 16:45:26');
INSERT INTO `sys_oper_log` VALUES ('106', '菜单管理', '1', 'com.zhaoxinms.web.controller.system.SysMenuController.add()', 'POST', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"orderNum\":\"1\",\"menuName\":\"日常收费（商铺）\",\"params\":{},\"parentId\":2000,\"isCache\":\"0\",\"path\":\"housepay\",\"component\":\"pms/housepay\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"C\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-12 17:03:23');
INSERT INTO `sys_oper_log` VALUES ('107', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"#\",\"orderNum\":\"1\",\"menuName\":\"商铺收费\",\"params\":{},\"parentId\":2000,\"isCache\":\"0\",\"path\":\"housepay\",\"component\":\"pms/housepay\",\"children\":[],\"createTime\":1636707803000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2001,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-12 17:07:45');
INSERT INTO `sys_oper_log` VALUES ('108', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"money\",\"orderNum\":\"1\",\"menuName\":\"商铺收费\",\"params\":{},\"parentId\":2000,\"isCache\":\"0\",\"path\":\"housepay\",\"component\":\"pms/housepay\",\"children\":[],\"createTime\":1636707803000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2001,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-12 17:08:17');
INSERT INTO `sys_oper_log` VALUES ('109', '菜单管理', '1', 'com.zhaoxinms.web.controller.system.SysMenuController.add()', 'POST', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"cascader\",\"orderNum\":\"3\",\"menuName\":\"基础数据管理\",\"params\":{},\"parentId\":0,\"isCache\":\"0\",\"path\":\"config\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"M\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-12 17:10:01');
INSERT INTO `sys_oper_log` VALUES ('110', '菜单管理', '1', 'com.zhaoxinms.web.controller.system.SysMenuController.add()', 'POST', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"list\",\"orderNum\":\"1\",\"menuName\":\"收费项管理\",\"params\":{},\"parentId\":2002,\"isCache\":\"0\",\"path\":\"configfeeitem\",\"component\":\"pms/configfeeitem\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"C\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-12 17:11:22');
INSERT INTO `sys_oper_log` VALUES ('111', '菜单管理', '1', 'com.zhaoxinms.web.controller.system.SysMenuController.add()', 'POST', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"nested\",\"orderNum\":\"2\",\"menuName\":\"商铺区域管理\",\"params\":{},\"parentId\":2002,\"isCache\":\"0\",\"path\":\"pms/confighouseblock\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"M\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-15 08:15:22');
INSERT INTO `sys_oper_log` VALUES ('112', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"nested\",\"orderNum\":\"2\",\"menuName\":\"商铺区域管理\",\"params\":{},\"parentId\":2002,\"isCache\":\"0\",\"path\":\"pms/confighouseblock\",\"component\":\"pms/configfeeitem\",\"children\":[],\"createTime\":1636935322000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2004,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-15 08:16:10');
INSERT INTO `sys_oper_log` VALUES ('113', '菜单管理', '1', 'com.zhaoxinms.web.controller.system.SysMenuController.add()', 'POST', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"component\",\"orderNum\":\"3\",\"menuName\":\"商铺管理\",\"params\":{},\"parentId\":2002,\"isCache\":\"0\",\"path\":\"config/house\",\"component\":\"pms/confighouse\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"C\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-15 08:27:15');
INSERT INTO `sys_oper_log` VALUES ('114', '菜单管理', '1', 'com.zhaoxinms.web.controller.system.SysMenuController.add()', 'POST', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"switch\",\"orderNum\":\"4\",\"menuName\":\"商铺租售\",\"params\":{},\"parentId\":2002,\"isCache\":\"0\",\"path\":\"config/contract\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"C\",\"perms\":\"pms/confighousecontract\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-15 09:03:03');
INSERT INTO `sys_oper_log` VALUES ('115', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"switch\",\"orderNum\":\"4\",\"menuName\":\"商铺租售\",\"params\":{},\"parentId\":2002,\"isCache\":\"0\",\"path\":\"config/contract\",\"component\":\"pms/confighousecontract\",\"children\":[],\"createTime\":1636938183000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2006,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-15 09:06:14');
INSERT INTO `sys_oper_log` VALUES ('116', '菜单管理', '1', 'com.zhaoxinms.web.controller.system.SysMenuController.add()', 'POST', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"build\",\"orderNum\":\"2\",\"menuName\":\"收费数据管理\",\"params\":{},\"parentId\":0,\"isCache\":\"0\",\"path\":\"fee\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"M\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-15 11:57:45');
INSERT INTO `sys_oper_log` VALUES ('117', '菜单管理', '1', 'com.zhaoxinms.web.controller.system.SysMenuController.add()', 'POST', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"edit\",\"orderNum\":\"2\",\"menuName\":\"商铺收费数据\",\"params\":{},\"parentId\":2007,\"isCache\":\"0\",\"path\":\"bill\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"C\",\"perms\":\"pms/paymentbill\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-15 11:59:55');
INSERT INTO `sys_oper_log` VALUES ('118', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"edit\",\"orderNum\":\"2\",\"menuName\":\"商铺收费数据\",\"params\":{},\"parentId\":2007,\"isCache\":\"0\",\"path\":\"bill\",\"component\":\"pms/paymentbill\",\"children\":[],\"createTime\":1636948795000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2008,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-15 12:03:02');
INSERT INTO `sys_oper_log` VALUES ('119', '菜单管理', '1', 'com.zhaoxinms.web.controller.system.SysMenuController.add()', 'POST', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"time-range\",\"orderNum\":\"1\",\"menuName\":\"抄表数据管理\",\"params\":{},\"parentId\":2007,\"isCache\":\"0\",\"path\":\"meter\",\"component\":\"pms/paymentmeter\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"C\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-15 12:08:27');
INSERT INTO `sys_oper_log` VALUES ('120', '菜单管理', '1', 'com.zhaoxinms.web.controller.system.SysMenuController.add()', 'POST', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"monitor\",\"orderNum\":\"2\",\"menuName\":\"临时收费\",\"params\":{},\"parentId\":0,\"isCache\":\"0\",\"path\":\"temppay\",\"component\":\"pms/paymenttemp\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"C\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-15 15:06:49');
INSERT INTO `sys_oper_log` VALUES ('121', '菜单管理', '1', 'com.zhaoxinms.web.controller.system.SysMenuController.add()', 'POST', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"monitor\",\"orderNum\":\"3\",\"menuName\":\"押金管理\",\"params\":{},\"parentId\":2000,\"isCache\":\"0\",\"path\":\"deposit\",\"component\":\"pms/paymentdeposit\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"C\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-15 15:08:42');
INSERT INTO `sys_oper_log` VALUES ('122', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"monitor\",\"orderNum\":\"2\",\"menuName\":\"临时收费\",\"params\":{},\"parentId\":2000,\"isCache\":\"0\",\"path\":\"temppay\",\"component\":\"pms/paymenttemp\",\"children\":[],\"createTime\":1636960009000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2010,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-15 15:09:03');
INSERT INTO `sys_oper_log` VALUES ('123', '菜单管理', '1', 'com.zhaoxinms.web.controller.system.SysMenuController.add()', 'POST', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"monitor\",\"orderNum\":\"4\",\"menuName\":\"预存款管理\",\"params\":{},\"parentId\":2000,\"isCache\":\"0\",\"path\":\"pre\",\"component\":\"pms/paymentpre\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"C\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-15 15:10:23');
INSERT INTO `sys_oper_log` VALUES ('124', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"nested\",\"orderNum\":\"2\",\"menuName\":\"商铺区域管理\",\"params\":{},\"parentId\":2002,\"isCache\":\"0\",\"path\":\"pms/confighouseblock\",\"component\":\"pms/confighouseblock\",\"children\":[],\"createTime\":1636935322000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2004,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-16 16:07:23');
INSERT INTO `sys_oper_log` VALUES ('125', '菜单管理', '1', 'com.zhaoxinms.web.controller.system.SysMenuController.add()', 'POST', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"monitor\",\"orderNum\":\"5\",\"menuName\":\"统计报表\",\"params\":{},\"parentId\":0,\"isCache\":\"0\",\"path\":\"statistics\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"M\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-18 14:12:12');
INSERT INTO `sys_oper_log` VALUES ('126', '菜单管理', '1', 'com.zhaoxinms.web.controller.system.SysMenuController.add()', 'POST', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"date-range\",\"orderNum\":\"1\",\"menuName\":\"个人费用明细\",\"params\":{},\"parentId\":2013,\"isCache\":\"0\",\"path\":\"personalDetail\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"M\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-18 14:21:15');
INSERT INTO `sys_oper_log` VALUES ('127', '菜单管理', '1', 'com.zhaoxinms.web.controller.system.SysMenuController.add()', 'POST', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"skill\",\"orderNum\":\"0\",\"menuName\":\"待生成的费用\",\"params\":{},\"parentId\":2013,\"isCache\":\"0\",\"path\":\"readyToGenerate\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"M\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-18 14:50:23');
INSERT INTO `sys_oper_log` VALUES ('128', '菜单管理', '1', 'com.zhaoxinms.web.controller.system.SysMenuController.add()', 'POST', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"chart\",\"orderNum\":\"3\",\"menuName\":\"收费统计（客户维度）\",\"params\":{},\"parentId\":2013,\"isCache\":\"0\",\"path\":\"userFee\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"M\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-18 14:52:00');
INSERT INTO `sys_oper_log` VALUES ('129', '菜单管理', '1', 'com.zhaoxinms.web.controller.system.SysMenuController.add()', 'POST', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"chart\",\"orderNum\":\"2\",\"menuName\":\"收费统计\",\"params\":{},\"parentId\":0,\"isCache\":\"0\",\"path\":\"feeStatistics\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"M\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-18 14:54:04');
INSERT INTO `sys_oper_log` VALUES ('130', '菜单管理', '1', 'com.zhaoxinms.web.controller.system.SysMenuController.add()', 'POST', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"chart\",\"orderNum\":\"3\",\"menuName\":\"收费统计\",\"params\":{},\"parentId\":2013,\"isCache\":\"0\",\"path\":\"fee\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"M\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-18 15:05:20');
INSERT INTO `sys_oper_log` VALUES ('131', '菜单管理', '1', 'com.zhaoxinms.web.controller.system.SysMenuController.add()', 'POST', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"druid\",\"orderNum\":\"5\",\"menuName\":\"欠费明细\",\"params\":{},\"parentId\":0,\"isCache\":\"0\",\"path\":\"expiration\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"M\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-18 15:11:00');
INSERT INTO `sys_oper_log` VALUES ('132', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"druid\",\"orderNum\":\"5\",\"menuName\":\"欠费明细\",\"params\":{},\"parentId\":2013,\"isCache\":\"0\",\"path\":\"expiration\",\"children\":[],\"createTime\":1637219460000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2019,\"menuType\":\"M\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-18 15:11:26');
INSERT INTO `sys_oper_log` VALUES ('133', '菜单管理', '3', 'com.zhaoxinms.web.controller.system.SysMenuController.remove()', 'DELETE', '1', 'admin', null, '/system/menu/2017', '127.0.0.1', '内网IP', '{menuId=2017}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-18 15:11:35');
INSERT INTO `sys_oper_log` VALUES ('134', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"chart\",\"orderNum\":\"2\",\"menuName\":\"收费统计\",\"params\":{},\"parentId\":2013,\"isCache\":\"0\",\"path\":\"fee\",\"children\":[],\"createTime\":1637219120000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2018,\"menuType\":\"M\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-18 15:11:58');
INSERT INTO `sys_oper_log` VALUES ('135', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"skill\",\"orderNum\":\"0\",\"menuName\":\"待生成的费用\",\"params\":{},\"parentId\":2013,\"isCache\":\"0\",\"path\":\"nextFee\",\"component\":\"statistics/nextFee\",\"children\":[],\"createTime\":1637218223000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2015,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-18 16:55:03');
INSERT INTO `sys_oper_log` VALUES ('136', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"date-range\",\"orderNum\":\"1\",\"menuName\":\"收费明细表\",\"params\":{},\"parentId\":2013,\"isCache\":\"0\",\"path\":\"payLog\",\"children\":[],\"createTime\":1637216475000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2014,\"menuType\":\"M\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-18 18:24:30');
INSERT INTO `sys_oper_log` VALUES ('137', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"date-range\",\"orderNum\":\"1\",\"menuName\":\"收费明细表\",\"params\":{},\"parentId\":2013,\"isCache\":\"0\",\"path\":\"payLog\",\"component\":\"statistics/paylog\",\"children\":[],\"createTime\":1637216475000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2014,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-19 11:32:43');
INSERT INTO `sys_oper_log` VALUES ('138', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"chart\",\"orderNum\":\"2\",\"menuName\":\"收费统计\",\"params\":{},\"parentId\":2013,\"isCache\":\"0\",\"path\":\"fee\",\"component\":\"statistics/payment\",\"children\":[],\"createTime\":1637219120000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2018,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-22 15:50:55');
INSERT INTO `sys_oper_log` VALUES ('139', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"chart\",\"orderNum\":\"3\",\"menuName\":\"收费统计（商铺）\",\"params\":{},\"parentId\":2013,\"isCache\":\"0\",\"path\":\"userFee\",\"children\":[],\"createTime\":1637218320000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2016,\"menuType\":\"M\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-23 14:47:16');
INSERT INTO `sys_oper_log` VALUES ('140', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"chart\",\"orderNum\":\"3\",\"menuName\":\"收费统计（商铺）\",\"params\":{},\"parentId\":2013,\"isCache\":\"0\",\"path\":\"userFee\",\"component\":\"statistics/payment/house\",\"children\":[],\"createTime\":1637218320000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2016,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-23 14:51:56');
INSERT INTO `sys_oper_log` VALUES ('141', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"druid\",\"orderNum\":\"5\",\"menuName\":\"预收款余额\",\"params\":{},\"parentId\":2013,\"isCache\":\"0\",\"path\":\"expiration\",\"children\":[],\"createTime\":1637219460000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2019,\"menuType\":\"M\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-23 16:59:00');
INSERT INTO `sys_oper_log` VALUES ('142', '菜单管理', '1', 'com.zhaoxinms.web.controller.system.SysMenuController.add()', 'POST', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"job\",\"orderNum\":\"6\",\"menuName\":\"收费日报表\",\"params\":{},\"parentId\":0,\"isCache\":\"0\",\"path\":\"dailyReport\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"M\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-23 17:19:55');
INSERT INTO `sys_oper_log` VALUES ('143', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"job\",\"orderNum\":\"6\",\"menuName\":\"收费日报表\",\"params\":{},\"parentId\":2013,\"isCache\":\"0\",\"path\":\"dailyReport\",\"children\":[],\"createTime\":1637659195000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2020,\"menuType\":\"M\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-23 17:20:10');
INSERT INTO `sys_oper_log` VALUES ('144', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"job\",\"orderNum\":\"6\",\"menuName\":\"收费日报表\",\"params\":{},\"parentId\":2013,\"isCache\":\"0\",\"path\":\"dailyReport\",\"component\":\"statistics/daily\",\"children\":[],\"createTime\":1637659195000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2020,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-23 17:34:33');
INSERT INTO `sys_oper_log` VALUES ('145', '菜单管理', '1', 'com.zhaoxinms.web.controller.system.SysMenuController.add()', 'POST', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"server\",\"orderNum\":\"7\",\"menuName\":\"欠费报表\",\"params\":{},\"parentId\":2013,\"isCache\":\"0\",\"path\":\"overdue\",\"component\":\"statistics/overdue\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"C\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-23 17:44:42');
INSERT INTO `sys_oper_log` VALUES ('146', '菜单管理', '1', 'com.zhaoxinms.web.controller.system.SysMenuController.add()', 'POST', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"excel\",\"orderNum\":\"8\",\"menuName\":\"收费台账\",\"params\":{},\"parentId\":2013,\"isCache\":\"0\",\"path\":\"book\",\"component\":\"statistics/book\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"C\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-23 17:47:28');
INSERT INTO `sys_oper_log` VALUES ('147', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"excel\",\"orderNum\":\"8\",\"menuName\":\"收费台账\",\"params\":{},\"parentId\":2013,\"isCache\":\"0\",\"path\":\"dailyFee\",\"component\":\"statistics/dailyFee\",\"children\":[],\"createTime\":1637660848000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2022,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-24 16:37:11');
INSERT INTO `sys_oper_log` VALUES ('148', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"excel\",\"orderNum\":\"8\",\"menuName\":\"收费报表（区分费用）\",\"params\":{},\"parentId\":2013,\"isCache\":\"0\",\"path\":\"dailyFee\",\"component\":\"statistics/dailyFee\",\"children\":[],\"createTime\":1637660848000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2022,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-24 16:37:49');
INSERT INTO `sys_oper_log` VALUES ('149', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"druid\",\"orderNum\":\"5\",\"menuName\":\"预收款余额\",\"params\":{},\"parentId\":2013,\"isCache\":\"0\",\"path\":\"preAccount\",\"component\":\"statistics/preAccount\",\"children\":[],\"createTime\":1637219460000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2019,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-25 16:13:15');
INSERT INTO `sys_oper_log` VALUES ('150', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"server\",\"orderNum\":\"9\",\"menuName\":\"欠费报表\",\"params\":{},\"parentId\":2013,\"isCache\":\"0\",\"path\":\"overdue\",\"component\":\"statistics/overdue\",\"children\":[],\"createTime\":1637660682000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2021,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-27 15:01:59');
INSERT INTO `sys_oper_log` VALUES ('151', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"job\",\"orderNum\":\"0\",\"menuName\":\"收费日报表\",\"params\":{},\"parentId\":2013,\"isCache\":\"0\",\"path\":\"dailyReport\",\"component\":\"statistics/daily\",\"children\":[],\"createTime\":1637659195000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2020,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-27 15:03:28');
INSERT INTO `sys_oper_log` VALUES ('152', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"excel\",\"orderNum\":\"1\",\"menuName\":\"收费报表（区分费用）\",\"params\":{},\"parentId\":2013,\"isCache\":\"0\",\"path\":\"dailyFee\",\"component\":\"statistics/dailyFee\",\"children\":[],\"createTime\":1637660848000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2022,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-27 15:03:37');
INSERT INTO `sys_oper_log` VALUES ('153', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"date-range\",\"orderNum\":\"2\",\"menuName\":\"收费明细表\",\"params\":{},\"parentId\":2013,\"isCache\":\"0\",\"path\":\"payLog\",\"component\":\"statistics/paylog\",\"children\":[],\"createTime\":1637216475000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2014,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-27 15:04:05');
INSERT INTO `sys_oper_log` VALUES ('154', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"chart\",\"orderNum\":\"8\",\"menuName\":\"收费统计\",\"params\":{},\"parentId\":2013,\"isCache\":\"0\",\"path\":\"fee\",\"component\":\"statistics/payment\",\"children\":[],\"createTime\":1637219120000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2018,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-27 15:07:00');
INSERT INTO `sys_oper_log` VALUES ('155', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"chart\",\"orderNum\":\"9\",\"menuName\":\"收费统计（商铺）\",\"params\":{},\"parentId\":2013,\"isCache\":\"0\",\"path\":\"userFee\",\"component\":\"statistics/payment/house\",\"children\":[],\"createTime\":1637218320000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2016,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-27 15:07:07');
INSERT INTO `sys_oper_log` VALUES ('156', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"skill\",\"orderNum\":\"4\",\"menuName\":\"待生成的费用\",\"params\":{},\"parentId\":2013,\"isCache\":\"0\",\"path\":\"nextFee\",\"component\":\"statistics/nextFee\",\"children\":[],\"createTime\":1637218223000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2015,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-27 15:07:20');
INSERT INTO `sys_oper_log` VALUES ('157', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"job\",\"orderNum\":\"0\",\"menuName\":\"收费日报表\",\"params\":{},\"parentId\":2013,\"isCache\":\"0\",\"path\":\"dailyReport\",\"component\":\"statistics/daily\",\"children\":[],\"createTime\":1637659195000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2020,\"menuType\":\"C\",\"perms\":\"statistics:dailyFee:dailyFee\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-30 16:44:47');
INSERT INTO `sys_oper_log` VALUES ('158', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"excel\",\"orderNum\":\"1\",\"menuName\":\"收费报表（区分费用）\",\"params\":{},\"parentId\":2013,\"isCache\":\"0\",\"path\":\"dailyFee\",\"component\":\"statistics/dailyFee\",\"children\":[],\"createTime\":1637660848000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2022,\"menuType\":\"C\",\"perms\":\"statistics:dailyFeeReport:dailyFeeReport\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-30 16:44:56');
INSERT INTO `sys_oper_log` VALUES ('159', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"date-range\",\"orderNum\":\"2\",\"menuName\":\"收费明细表\",\"params\":{},\"parentId\":2013,\"isCache\":\"0\",\"path\":\"payLog\",\"component\":\"statistics/paylog\",\"children\":[],\"createTime\":1637216475000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2014,\"menuType\":\"C\",\"perms\":\"statistics:paymentBill:overdue\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-30 16:45:19');
INSERT INTO `sys_oper_log` VALUES ('160', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"skill\",\"orderNum\":\"4\",\"menuName\":\"待生成的费用\",\"params\":{},\"parentId\":2013,\"isCache\":\"0\",\"path\":\"nextFee\",\"component\":\"statistics/nextFee\",\"children\":[],\"createTime\":1637218223000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2015,\"menuType\":\"C\",\"perms\":\"statistics:nextFee:nextFee\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-30 16:51:26');
INSERT INTO `sys_oper_log` VALUES ('161', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"druid\",\"orderNum\":\"5\",\"menuName\":\"预收款余额\",\"params\":{},\"parentId\":2013,\"isCache\":\"0\",\"path\":\"preAccount\",\"component\":\"statistics/preAccount\",\"children\":[],\"createTime\":1637219460000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2019,\"menuType\":\"C\",\"perms\":\"statistics:preAccount:list\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-30 16:51:51');
INSERT INTO `sys_oper_log` VALUES ('162', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"chart\",\"orderNum\":\"8\",\"menuName\":\"收费统计\",\"params\":{},\"parentId\":2013,\"isCache\":\"0\",\"path\":\"fee\",\"component\":\"statistics/payment\",\"children\":[],\"createTime\":1637219120000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2018,\"menuType\":\"C\",\"perms\":\"@PreAuthorize(\\\"@ss.hasPermi(\'statistics:PaymentStatistics:PaymentStatistics\')\\\")\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-30 16:54:11');
INSERT INTO `sys_oper_log` VALUES ('163', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"chart\",\"orderNum\":\"8\",\"menuName\":\"收费统计\",\"params\":{},\"parentId\":2013,\"isCache\":\"0\",\"path\":\"fee\",\"component\":\"statistics/payment\",\"children\":[],\"createTime\":1637219120000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2018,\"menuType\":\"C\",\"perms\":\"statistics:PaymentStatistics:PaymentStatistics\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-30 16:54:41');
INSERT INTO `sys_oper_log` VALUES ('164', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"chart\",\"orderNum\":\"9\",\"menuName\":\"收费统计（商铺）\",\"params\":{},\"parentId\":2013,\"isCache\":\"0\",\"path\":\"userFee\",\"component\":\"statistics/payment/house\",\"children\":[],\"createTime\":1637218320000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2016,\"menuType\":\"C\",\"perms\":\"statistics:PaymentStatistics:house\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-30 16:54:54');
INSERT INTO `sys_oper_log` VALUES ('165', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"server\",\"orderNum\":\"9\",\"menuName\":\"欠费报表\",\"params\":{},\"parentId\":2013,\"isCache\":\"0\",\"path\":\"overdue\",\"component\":\"statistics/overdue\",\"children\":[],\"createTime\":1637660682000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2021,\"menuType\":\"C\",\"perms\":\"statistics:paymentBill:overdue\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-30 16:58:46');
INSERT INTO `sys_oper_log` VALUES ('166', '用户管理', '3', 'com.zhaoxinms.web.controller.system.SysUserController.remove()', 'DELETE', '1', 'admin', null, '/system/user/2', '127.0.0.1', '内网IP', '{userIds=2}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-30 16:59:06');
INSERT INTO `sys_oper_log` VALUES ('167', '用户管理', '1', 'com.zhaoxinms.web.controller.system.SysUserController.add()', 'POST', '1', 'admin', null, '/system/user', '127.0.0.1', '内网IP', '{\"admin\":false,\"password\":\"$2a$10$/5eXAPSba0q0TJ1R.gdYCO5JKOgPs2YcIBr7Z596eZnVKpA5M/M7i\",\"postIds\":[],\"nickName\":\"李四\",\"deptId\":100,\"params\":{},\"userName\":\"fanhuibin\",\"userId\":3,\"createBy\":\"admin\",\"roleIds\":[],\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-30 17:00:44');
INSERT INTO `sys_oper_log` VALUES ('168', '角色管理', '1', 'com.zhaoxinms.web.controller.system.SysRoleController.add()', 'POST', '1', 'admin', null, '/system/role', '127.0.0.1', '内网IP', '{\"flag\":false,\"roleId\":3,\"admin\":false,\"params\":{},\"roleSort\":\"0\",\"deptCheckStrictly\":true,\"createBy\":\"admin\",\"menuCheckStrictly\":true,\"roleKey\":\"manager\",\"roleName\":\"管理员\",\"deptIds\":[],\"menuIds\":[2013,2020,2022,2014,2015,2019,2018,2016,2021],\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-30 17:59:47');
INSERT INTO `sys_oper_log` VALUES ('169', '角色管理', '4', 'com.zhaoxinms.web.controller.system.SysRoleController.selectAuthUserAll()', 'PUT', '1', 'admin', null, '/system/role/authUser/selectAll', '127.0.0.1', '内网IP', '3 [3]', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-30 18:00:40');
INSERT INTO `sys_oper_log` VALUES ('170', '部门管理', '2', 'com.zhaoxinms.web.controller.system.SysDeptController.edit()', 'PUT', '1', 'admin', null, '/system/dept', '127.0.0.1', '内网IP', '{\"deptName\":\"肇新科技\",\"leader\":\"若依\",\"deptId\":100,\"orderNum\":\"0\",\"delFlag\":\"0\",\"params\":{},\"parentId\":0,\"createBy\":\"admin\",\"children\":[],\"createTime\":1636626924000,\"phone\":\"\",\"updateBy\":\"admin\",\"ancestors\":\"0\",\"email\":\"ry@qq.com\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-30 18:21:52');
INSERT INTO `sys_oper_log` VALUES ('171', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"money\",\"orderNum\":\"1\",\"menuName\":\"商铺收费\",\"params\":{},\"parentId\":2000,\"isCache\":\"0\",\"path\":\"housepay\",\"component\":\"pms/housepay\",\"children\":[],\"createTime\":1636707803000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2001,\"menuType\":\"C\",\"perms\":\"payment:paymentBill:payBill\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-30 18:32:49');
INSERT INTO `sys_oper_log` VALUES ('172', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"money\",\"orderNum\":\"1\",\"menuName\":\"商铺收费\",\"params\":{},\"parentId\":2000,\"isCache\":\"0\",\"path\":\"housepay\",\"component\":\"pms/housepay\",\"children\":[],\"createTime\":1636707803000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2001,\"menuType\":\"C\",\"perms\":\"payment:paymentBill:payBill\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-30 18:37:50');
INSERT INTO `sys_oper_log` VALUES ('173', '角色管理', '2', 'com.zhaoxinms.web.controller.system.SysRoleController.edit()', 'PUT', '1', 'admin', null, '/system/role', '127.0.0.1', '内网IP', '{\"flag\":false,\"roleId\":3,\"admin\":false,\"dataScope\":\"1\",\"delFlag\":\"0\",\"params\":{},\"roleSort\":\"0\",\"deptCheckStrictly\":true,\"createTime\":1638266387000,\"updateBy\":\"admin\",\"menuCheckStrictly\":true,\"roleKey\":\"manager\",\"roleName\":\"管理员\",\"menuIds\":[2000,2001,2013,2020,2022,2014,2015,2019,2018,2016,2021],\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-30 18:41:28');
INSERT INTO `sys_oper_log` VALUES ('174', '菜单管理', '1', 'com.zhaoxinms.web.controller.system.SysMenuController.add()', 'POST', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"orderNum\":\"1\",\"menuName\":\"查询收款数据\",\"params\":{},\"parentId\":2001,\"isCache\":\"0\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"F\",\"perms\":\"payment:paymentBill:list\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-30 18:49:30');
INSERT INTO `sys_oper_log` VALUES ('175', '角色管理', '1', 'com.zhaoxinms.web.controller.system.SysRoleController.add()', 'POST', '1', 'admin', null, '/system/role', '127.0.0.1', '内网IP', '{\"flag\":false,\"roleId\":4,\"admin\":false,\"params\":{},\"roleSort\":\"0\",\"deptCheckStrictly\":true,\"createBy\":\"admin\",\"menuCheckStrictly\":true,\"roleKey\":\"payee\",\"roleName\":\"收费员\",\"deptIds\":[],\"menuIds\":[],\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-30 18:53:50');
INSERT INTO `sys_oper_log` VALUES ('176', '角色管理', '2', 'com.zhaoxinms.web.controller.system.SysRoleController.edit()', 'PUT', '1', 'admin', null, '/system/role', '127.0.0.1', '内网IP', '{\"flag\":false,\"roleId\":4,\"admin\":false,\"dataScope\":\"1\",\"delFlag\":\"0\",\"params\":{},\"roleSort\":\"0\",\"deptCheckStrictly\":true,\"createTime\":1638269630000,\"updateBy\":\"admin\",\"menuCheckStrictly\":true,\"roleKey\":\"payee\",\"roleName\":\"收费员\",\"menuIds\":[2000,2001,2023,2010,2011,2012],\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-11-30 18:54:56');
INSERT INTO `sys_oper_log` VALUES ('177', '用户管理', '4', 'com.zhaoxinms.web.controller.system.SysUserController.insertAuthRole()', 'PUT', '1', 'admin', null, '/system/user/authRole', '127.0.0.1', '内网IP', '3 [4]', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-12-01 08:52:22');
INSERT INTO `sys_oper_log` VALUES ('178', '临时收费', '1', 'com.zhaoxinms.payment.controller.PaymentTempController.create()', 'POST', '1', 'fanhuibin', null, '/payment/PaymentTemp', '127.0.0.1', '内网IP', '{\"feeItemId\":\"1456546272025604098\",\"resourceId\":\"1438429781237432325\",\"payType\":\"9d46e7f3729843ef8dc35f9987f7f2fe\",\"operateTime\":1638319996579,\"amt\":200,\"block\":\"01\",\"resourceName\":\"01-006\",\"feeUser\":\"短租甲\"}', '{\"code\":200,\"msg\":\"新增费用成功\"}', '0', null, '2021-12-01 08:53:24');
INSERT INTO `sys_oper_log` VALUES ('179', '创建押金', '1', 'com.zhaoxinms.payment.controller.PaymentDepositController.create()', 'POST', '1', 'fanhuibin', null, '/payment/PaymentDeposit', '127.0.0.1', '内网IP', '{\"feeItemId\":\"1456543121520291842\",\"resourceId\":\"1438429781237432325\",\"payType\":\"9d46e7f3729843ef8dc35f9987f7f2fe\",\"operateTime\":1638320008765,\"amt\":300,\"block\":\"01\",\"resourceName\":\"01-006\",\"feeUser\":\"短租甲\"}', '{\"code\":200,\"msg\":\"新增押金成功\"}', '0', null, '2021-12-01 08:53:36');
INSERT INTO `sys_oper_log` VALUES ('180', '预付款退款', '2', 'com.zhaoxinms.payment.controller.PaymentPreController.withdraw()', 'POST', '1', 'fanhuibin', null, '/payment/PaymentPre/refund', '127.0.0.1', '内网IP', '{\"resourceId\":\"1438429781237432325\",\"payType\":\"9d46e7f3729843ef8dc35f9987f7f2fe\",\"refundItems\":[{\"id\":\"1464080210210189314\",\"resourceId\":\"1438429781237432325\",\"block\":\"01\",\"feeItemId\":\"\",\"feeItemName\":\"不指定收费项\",\"amt\":\"2918.12\",\"resourceName\":\"01-006\",\"USE_FEE_ITEM\":\"0\",\"changeMoney\":\"200\"}],\"operateTime\":1638320022972,\"block\":\"01\",\"resourceName\":\"01-006\",\"feeUser\":\"短租甲\"}', '{\"code\":200,\"msg\":\"预付款退还成功\"}', '0', null, '2021-12-01 08:53:50');
INSERT INTO `sys_oper_log` VALUES ('181', '部门管理', '3', 'com.zhaoxinms.web.controller.system.SysDeptController.remove()', 'DELETE', '1', 'admin', null, '/system/dept/101', '127.0.0.1', '内网IP', '{deptId=101}', '{\"msg\":\"存在下级部门,不允许删除\",\"code\":500}', '0', null, '2021-12-01 10:27:03');
INSERT INTO `sys_oper_log` VALUES ('182', '部门管理', '3', 'com.zhaoxinms.web.controller.system.SysDeptController.remove()', 'DELETE', '1', 'admin', null, '/system/dept/103', '127.0.0.1', '内网IP', '{deptId=103}', '{\"msg\":\"部门存在用户,不允许删除\",\"code\":500}', '0', null, '2021-12-01 10:27:07');
INSERT INTO `sys_oper_log` VALUES ('183', '部门管理', '3', 'com.zhaoxinms.web.controller.system.SysDeptController.remove()', 'DELETE', '1', 'admin', null, '/system/dept/103', '127.0.0.1', '内网IP', '{deptId=103}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-12-01 10:28:05');
INSERT INTO `sys_oper_log` VALUES ('184', '部门管理', '3', 'com.zhaoxinms.web.controller.system.SysDeptController.remove()', 'DELETE', '1', 'admin', null, '/system/dept/104', '127.0.0.1', '内网IP', '{deptId=104}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-12-01 10:28:07');
INSERT INTO `sys_oper_log` VALUES ('185', '角色管理', '2', 'com.zhaoxinms.web.controller.system.SysRoleController.dataScope()', 'PUT', '1', 'admin', null, '/system/role/dataScope', '127.0.0.1', '内网IP', '{\"flag\":false,\"roleId\":2,\"admin\":false,\"remark\":\"普通角色\",\"dataScope\":\"2\",\"delFlag\":\"0\",\"params\":{},\"roleSort\":\"2\",\"deptCheckStrictly\":true,\"createTime\":1636626924000,\"menuCheckStrictly\":true,\"roleKey\":\"common\",\"roleName\":\"普通角色\",\"deptIds\":[],\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-12-01 10:29:30');
INSERT INTO `sys_oper_log` VALUES ('186', '菜单管理', '3', 'com.zhaoxinms.web.controller.system.SysMenuController.remove()', 'DELETE', '1', 'admin', null, '/system/menu/2023', '127.0.0.1', '内网IP', '{menuId=2023}', '{\"msg\":\"菜单已分配,不允许删除\",\"code\":500}', '0', null, '2021-12-01 10:56:26');
INSERT INTO `sys_oper_log` VALUES ('187', '角色管理', '2', 'com.zhaoxinms.web.controller.system.SysRoleController.edit()', 'PUT', '1', 'admin', null, '/system/role', '127.0.0.1', '内网IP', '{\"flag\":false,\"roleId\":4,\"admin\":false,\"dataScope\":\"1\",\"delFlag\":\"0\",\"params\":{},\"roleSort\":\"0\",\"deptCheckStrictly\":true,\"createTime\":1638269630000,\"updateBy\":\"admin\",\"menuCheckStrictly\":true,\"roleKey\":\"payee\",\"roleName\":\"收费员\",\"menuIds\":[2000,2010,2011,2012],\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-12-01 10:58:02');
INSERT INTO `sys_oper_log` VALUES ('188', '菜单管理', '3', 'com.zhaoxinms.web.controller.system.SysMenuController.remove()', 'DELETE', '1', 'admin', null, '/system/menu/2023', '127.0.0.1', '内网IP', '{menuId=2023}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-12-01 10:58:14');
INSERT INTO `sys_oper_log` VALUES ('189', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"money\",\"orderNum\":\"1\",\"menuName\":\"商铺收费\",\"params\":{},\"parentId\":2000,\"isCache\":\"0\",\"path\":\"housepay\",\"component\":\"pms/housepay\",\"children\":[],\"createTime\":1636707803000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2001,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-12-01 10:58:21');
INSERT INTO `sys_oper_log` VALUES ('190', '角色管理', '2', 'com.zhaoxinms.web.controller.system.SysRoleController.edit()', 'PUT', '1', 'admin', null, '/system/role', '127.0.0.1', '内网IP', '{\"flag\":false,\"roleId\":4,\"admin\":false,\"dataScope\":\"1\",\"delFlag\":\"0\",\"params\":{},\"roleSort\":\"0\",\"deptCheckStrictly\":true,\"createTime\":1638269630000,\"updateBy\":\"admin\",\"menuCheckStrictly\":true,\"roleKey\":\"payee\",\"roleName\":\"收费员\",\"menuIds\":[2000,2001,2010,2011,2012],\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-12-01 10:59:20');
INSERT INTO `sys_oper_log` VALUES ('191', '角色管理', '2', 'com.zhaoxinms.web.controller.system.SysRoleController.edit()', 'PUT', '1', 'admin', null, '/system/role', '127.0.0.1', '内网IP', '{\"flag\":false,\"roleId\":4,\"admin\":false,\"dataScope\":\"1\",\"delFlag\":\"0\",\"params\":{},\"roleSort\":\"0\",\"deptCheckStrictly\":true,\"createTime\":1638269630000,\"updateBy\":\"admin\",\"menuCheckStrictly\":true,\"roleKey\":\"payee\",\"roleName\":\"收费员\",\"menuIds\":[2013,2000,2001,2010,2011,2012,2015,2021],\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-12-01 11:05:27');
INSERT INTO `sys_oper_log` VALUES ('192', '角色管理', '2', 'com.zhaoxinms.web.controller.system.SysRoleController.edit()', 'PUT', '1', 'admin', null, '/system/role', '127.0.0.1', '内网IP', '{\"flag\":false,\"roleId\":3,\"admin\":false,\"dataScope\":\"1\",\"delFlag\":\"0\",\"params\":{},\"roleSort\":\"0\",\"deptCheckStrictly\":true,\"createTime\":1638266387000,\"updateBy\":\"admin\",\"menuCheckStrictly\":true,\"roleKey\":\"manager\",\"roleName\":\"管理员\",\"menuIds\":[2000,2001,2007,2009,2008,2002,2003,2004,2005,2006,2013,2020,2022,2014,2015,2019,2018,2016,2021,1,100,1001,1002,1003,1004,1005,1006,1007,101,1008,1009,1010,1011,1012,102,1013,1014,1015,1016,103,1017,1018,1019,1020,104,1021,1022,1023,1024,1025,105,1026,1027,1028,1029,1030,106,1031,1032,1033,1034,1035,107,1036,1037,1038,1039,108,500,1040,1041,1042,501,1043,1044,1045,2,109,1046,1047,1048,110,1049,1050,1051,1052,1053,1054,111,112,113,3,114,115,1055,1056,1058,1057,1059,1060,116,4],\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-12-01 11:09:52');
INSERT INTO `sys_oper_log` VALUES ('193', '角色管理', '2', 'com.zhaoxinms.web.controller.system.SysRoleController.edit()', 'PUT', '1', 'admin', null, '/system/role', '127.0.0.1', '内网IP', '{\"flag\":false,\"roleId\":4,\"admin\":false,\"dataScope\":\"1\",\"delFlag\":\"0\",\"params\":{},\"roleSort\":\"0\",\"deptCheckStrictly\":true,\"createTime\":1638269630000,\"updateBy\":\"admin\",\"menuCheckStrictly\":true,\"roleKey\":\"payee\",\"roleName\":\"收费员\",\"menuIds\":[2013,2000,2001,2010,2011,2012,2015,2021,4],\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-12-01 11:10:09');
INSERT INTO `sys_oper_log` VALUES ('194', '角色管理', '1', 'com.zhaoxinms.web.controller.system.SysRoleController.add()', 'POST', '1', 'admin', null, '/system/role', '127.0.0.1', '内网IP', '{\"flag\":false,\"roleId\":5,\"admin\":false,\"params\":{},\"roleSort\":\"0\",\"deptCheckStrictly\":true,\"createBy\":\"admin\",\"menuCheckStrictly\":true,\"roleKey\":\"finance\",\"roleName\":\"财务人员\",\"deptIds\":[],\"menuIds\":[2013,2020,2022,2014,2015,2019,2018,2016,2021],\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-12-01 11:11:19');
INSERT INTO `sys_oper_log` VALUES ('195', '用户管理', '1', 'com.zhaoxinms.web.controller.system.SysUserController.add()', 'POST', '1', 'admin', null, '/system/user', '127.0.0.1', '内网IP', '{\"admin\":false,\"password\":\"$2a$10$E4b04cnxT/pkTBYsoQRT/OxlJugehiyx2OGF8e0wJ4l70AIta/Blm\",\"postIds\":[],\"nickName\":\"财务人员\",\"params\":{},\"userName\":\"finance\",\"userId\":4,\"createBy\":\"admin\",\"roleIds\":[],\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-12-01 11:14:31');
INSERT INTO `sys_oper_log` VALUES ('196', '用户管理', '4', 'com.zhaoxinms.web.controller.system.SysUserController.insertAuthRole()', 'PUT', '1', 'admin', null, '/system/user/authRole', '127.0.0.1', '内网IP', '4 [5]', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-12-01 11:14:54');
INSERT INTO `sys_oper_log` VALUES ('197', '用户管理', '1', 'com.zhaoxinms.web.controller.system.SysUserController.add()', 'POST', '1', 'admin', null, '/system/user', '127.0.0.1', '内网IP', '{\"admin\":false,\"password\":\"$2a$10$UQHu72ceDt2t6JRwsWylnOEN1duS45x4DVBZPXUrJI.vBTbZ0lnzm\",\"postIds\":[],\"nickName\":\"管理员\",\"deptId\":100,\"params\":{},\"userName\":\"manager\",\"userId\":5,\"createBy\":\"admin\",\"roleIds\":[],\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-12-01 11:15:46');
INSERT INTO `sys_oper_log` VALUES ('198', '用户管理', '2', 'com.zhaoxinms.web.controller.system.SysUserController.edit()', 'PUT', '1', 'admin', null, '/system/user', '127.0.0.1', '内网IP', '{\"roles\":[{\"flag\":false,\"roleId\":5,\"admin\":false,\"dataScope\":\"1\",\"params\":{},\"roleSort\":\"0\",\"deptCheckStrictly\":false,\"menuCheckStrictly\":false,\"roleKey\":\"finance\",\"roleName\":\"财务人员\",\"status\":\"0\"}],\"phonenumber\":\"\",\"admin\":false,\"delFlag\":\"0\",\"password\":\"\",\"updateBy\":\"admin\",\"postIds\":[],\"loginIp\":\"\",\"email\":\"\",\"nickName\":\"财务人员\",\"sex\":\"0\",\"deptId\":100,\"avatar\":\"\",\"params\":{},\"userName\":\"finance\",\"userId\":4,\"createBy\":\"admin\",\"roleIds\":[5],\"createTime\":1638328471000,\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-12-01 11:15:54');
INSERT INTO `sys_oper_log` VALUES ('199', '用户管理', '4', 'com.zhaoxinms.web.controller.system.SysUserController.insertAuthRole()', 'PUT', '1', 'admin', null, '/system/user/authRole', '127.0.0.1', '内网IP', '5 [3]', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-12-01 11:16:50');
INSERT INTO `sys_oper_log` VALUES ('200', '角色管理', '2', 'com.zhaoxinms.web.controller.system.SysRoleController.edit()', 'PUT', '1', 'admin', null, '/system/role', '127.0.0.1', '内网IP', '{\"flag\":false,\"roleId\":3,\"admin\":false,\"dataScope\":\"1\",\"delFlag\":\"0\",\"params\":{},\"roleSort\":\"0\",\"deptCheckStrictly\":true,\"createTime\":1638266387000,\"updateBy\":\"admin\",\"menuCheckStrictly\":true,\"roleKey\":\"manager\",\"roleName\":\"管理员\",\"menuIds\":[2000,2001,2002,2003,2004,2005,2006,2013,2020,2022,2014,2015,2019,2018,2016,2021,1,100,1001,1002,1003,1004,1005,1006,1007,101,1008,1009,1010,1011,1012,102,1013,1014,1015,1016,103,1017,1018,1019,1020,104,1021,1022,1023,1024,1025,105,1026,1027,1028,1029,1030,106,1031,1032,1033,1034,1035,107,1036,1037,1038,1039,108,500,1040,1041,1042,501,1043,1044,1045,2,109,1046,1047,1048,110,1049,1050,1051,1052,1053,1054,111,112,113,3,114,115,1055,1056,1058,1057,1059,1060,116,4],\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-12-01 11:25:31');
INSERT INTO `sys_oper_log` VALUES ('201', '角色管理', '2', 'com.zhaoxinms.web.controller.system.SysRoleController.edit()', 'PUT', '1', 'admin', null, '/system/role', '127.0.0.1', '内网IP', '{\"flag\":false,\"roleId\":3,\"admin\":false,\"dataScope\":\"1\",\"delFlag\":\"0\",\"params\":{},\"roleSort\":\"0\",\"deptCheckStrictly\":true,\"createTime\":1638266387000,\"updateBy\":\"admin\",\"menuCheckStrictly\":true,\"roleKey\":\"manager\",\"roleName\":\"管理员\",\"menuIds\":[2007,2009,2008,2002,2003,2004,2005,2006,2013,2020,2022,2014,2015,2019,2018,2016,2021,1,100,1001,1002,1003,1004,1005,1006,1007,101,1008,1009,1010,1011,1012,102,1013,1014,1015,1016,103,1017,1018,1019,1020,104,1021,1022,1023,1024,1025,105,1026,1027,1028,1029,1030,106,1031,1032,1033,1034,1035,107,1036,1037,1038,1039,108,500,1040,1041,1042,501,1043,1044,1045,2,109,1046,1047,1048,110,1049,1050,1051,1052,1053,1054,111,112,113,3,114,115,1055,1056,1058,1057,1059,1060,116,4],\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-12-01 11:28:41');
INSERT INTO `sys_oper_log` VALUES ('202', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"query\":\"\",\"icon\":\"guide\",\"orderNum\":\"40\",\"menuName\":\"肇新智慧物业\",\"params\":{},\"parentId\":0,\"isCache\":\"0\",\"path\":\"http://ruoyi.vip\",\"children\":[],\"createTime\":1636626924000,\"updateBy\":\"admin\",\"isFrame\":\"0\",\"menuId\":4,\"menuType\":\"M\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-12-02 18:36:13');
INSERT INTO `sys_oper_log` VALUES ('203', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"shopping\",\"orderNum\":\"1\",\"menuName\":\"物业收费\",\"params\":{},\"parentId\":0,\"isCache\":\"0\",\"path\":\"payment\",\"children\":[],\"createTime\":1636706690000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2000,\"menuType\":\"M\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-12-07 21:00:56');
INSERT INTO `sys_oper_log` VALUES ('204', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"money\",\"orderNum\":\"1\",\"menuName\":\"商铺综合收费\",\"params\":{},\"parentId\":2000,\"isCache\":\"0\",\"path\":\"housepay\",\"component\":\"pms/housepay\",\"children\":[],\"createTime\":1636707803000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2001,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-12-07 21:02:14');
INSERT INTO `sys_oper_log` VALUES ('205', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"nested\",\"orderNum\":\"2\",\"menuName\":\"商圈管理\",\"params\":{},\"parentId\":2002,\"isCache\":\"0\",\"path\":\"pms/confighouseblock\",\"component\":\"pms/confighouseblock\",\"children\":[],\"createTime\":1636935322000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2004,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-12-07 21:02:48');
INSERT INTO `sys_oper_log` VALUES ('206', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"example\",\"orderNum\":\"2\",\"menuName\":\"商圈管理\",\"params\":{},\"parentId\":2002,\"isCache\":\"0\",\"path\":\"pms/confighouseblock\",\"component\":\"pms/confighouseblock\",\"children\":[],\"createTime\":1636935322000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2004,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-12-07 21:03:24');
INSERT INTO `sys_oper_log` VALUES ('207', '商圈删除', '3', 'com.zhaoxinms.baseconfig.controller.ConfigHouseBlockController.delete()', 'DELETE', '1', 'admin', null, '/baseconfig/ConfigHouseBlock/2c8bdb82f8654c2aa28e50d8a6e09e73', '127.0.0.1', '内网IP', '{id=2c8bdb82f8654c2aa28e50d8a6e09e73}', null, '1', '暂未实现', '2021-12-07 21:04:27');
INSERT INTO `sys_oper_log` VALUES ('208', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"form\",\"orderNum\":\"2\",\"menuName\":\"临时收费\",\"params\":{},\"parentId\":2000,\"isCache\":\"0\",\"path\":\"temppay\",\"component\":\"pms/paymenttemp\",\"children\":[],\"createTime\":1636960009000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2010,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-12-07 21:05:46');
INSERT INTO `sys_oper_log` VALUES ('209', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"dict\",\"orderNum\":\"3\",\"menuName\":\"押金管理\",\"params\":{},\"parentId\":2000,\"isCache\":\"0\",\"path\":\"deposit\",\"component\":\"pms/paymentdeposit\",\"children\":[],\"createTime\":1636960122000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2011,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-12-07 21:06:18');
INSERT INTO `sys_oper_log` VALUES ('210', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"documentation\",\"orderNum\":\"4\",\"menuName\":\"预存款管理\",\"params\":{},\"parentId\":2000,\"isCache\":\"0\",\"path\":\"pre\",\"component\":\"pms/paymentpre\",\"children\":[],\"createTime\":1636960223000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2012,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-12-07 21:07:59');
INSERT INTO `sys_oper_log` VALUES ('211', '商圈删除', '3', 'com.zhaoxinms.baseconfig.controller.ConfigHouseBlockController.delete()', 'DELETE', '1', 'admin', null, '/baseconfig/ConfigHouseBlock/2c8bdb82f8654c2aa28e50d8a6e09e73', '127.0.0.1', '内网IP', '{id=2c8bdb82f8654c2aa28e50d8a6e09e73}', '{\"code\":200,\"msg\":\"删除成功\"}', '0', null, '2021-12-08 09:26:53');
INSERT INTO `sys_oper_log` VALUES ('212', '商圈删除', '3', 'com.zhaoxinms.baseconfig.controller.ConfigHouseBlockController.delete()', 'DELETE', '1', 'admin', null, '/baseconfig/ConfigHouseBlock/f76112311fba4961aa4326f3be0005bc', '127.0.0.1', '内网IP', '{id=f76112311fba4961aa4326f3be0005bc}', null, '1', '\'01-002\'非空置中的商铺不允许删除', '2021-12-08 09:26:59');
INSERT INTO `sys_oper_log` VALUES ('213', '商圈删除', '3', 'com.zhaoxinms.baseconfig.controller.ConfigHouseBlockController.delete()', 'DELETE', '1', 'admin', null, '/baseconfig/ConfigHouseBlock/f76112311fba4961aa4326f3be0005bc', '127.0.0.1', '内网IP', '{id=f76112311fba4961aa4326f3be0005bc}', null, '1', '编号\'01-002\'的商铺非空置状态，不允许删除', '2021-12-08 09:28:10');
INSERT INTO `sys_oper_log` VALUES ('214', '商圈删除', '3', 'com.zhaoxinms.baseconfig.controller.ConfigHouseBlockController.delete()', 'DELETE', '1', 'admin', null, '/baseconfig/ConfigHouseBlock/f76112311fba4961aa4326f3be0005bc', '127.0.0.1', '内网IP', '{id=f76112311fba4961aa4326f3be0005bc}', null, '1', '编号\'01-002\'的商铺非空置状态，不允许删除', '2021-12-08 09:28:17');
INSERT INTO `sys_oper_log` VALUES ('215', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"example\",\"orderNum\":\"2\",\"menuName\":\"商业区管理\",\"params\":{},\"parentId\":2002,\"isCache\":\"0\",\"path\":\"pms/confighouseblock\",\"component\":\"pms/confighouseblock\",\"children\":[],\"createTime\":1636935322000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2004,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-12-08 09:35:02');
INSERT INTO `sys_oper_log` VALUES ('216', '商业区删除', '3', 'com.zhaoxinms.baseconfig.controller.ConfigHouseBlockController.delete()', 'DELETE', '1', 'admin', null, '/baseconfig/ConfigHouseBlock/f76112311fba4961aa4326f3be0005bc', '127.0.0.1', '内网IP', '{id=f76112311fba4961aa4326f3be0005bc}', null, '1', '编号\'01-002\'的商铺非空置状态，不允许删除', '2021-12-08 09:35:19');
INSERT INTO `sys_oper_log` VALUES ('217', '商业区删除', '3', 'com.zhaoxinms.baseconfig.controller.ConfigHouseBlockController.delete()', 'DELETE', '1', 'admin', null, '/baseconfig/ConfigHouseBlock/f76112311fba4961aa4326f3be0005bc', '127.0.0.1', '内网IP', '{id=f76112311fba4961aa4326f3be0005bc}', null, '1', '编号\'01-002\'的商铺非空置状态，不允许删除', '2021-12-08 09:35:24');
INSERT INTO `sys_oper_log` VALUES ('218', '商业区删除', '3', 'com.zhaoxinms.baseconfig.controller.ConfigHouseBlockController.delete()', 'DELETE', '1', 'admin', null, '/baseconfig/ConfigHouseBlock/f76112311fba4961aa4326f3be0005bc', '127.0.0.1', '内网IP', '{id=f76112311fba4961aa4326f3be0005bc}', null, '1', '编号\'01-002\'的商铺非空置状态，不允许删除', '2021-12-08 09:42:40');
INSERT INTO `sys_oper_log` VALUES ('219', '商业区删除', '3', 'com.zhaoxinms.baseconfig.controller.ConfigHouseBlockController.delete()', 'DELETE', '1', 'admin', null, '/baseconfig/ConfigHouseBlock/f76112311fba4961aa4326f3be0005bc', '127.0.0.1', '内网IP', '{id=f76112311fba4961aa4326f3be0005bc}', null, '1', '编号\'01-002\'的商铺非空置状态，不允许删除', '2021-12-08 09:43:24');
INSERT INTO `sys_oper_log` VALUES ('220', '商业区创建', '1', 'com.zhaoxinms.baseconfig.controller.ConfigHouseBlockController.create()', 'POST', '1', 'admin', null, '/baseconfig/ConfigHouseBlock', '127.0.0.1', '内网IP', '{\"code\":\"0012\",\"name\":\"阿松大发生的发生的发士大夫阿松大飞\",\"remark\":\"阿斯蒂芬阿斯蒂芬阿斯蒂芬阿斯蒂芬阿斯蒂芬阿斯蒂芬阿斯蒂芬阿松大发士大夫阿松大发士大夫阿松大飞洒地方阿斯蒂芬阿斯蒂芬阿松大发士大夫阿斯蒂芬发生大法师发生的发生的发士大夫阿松大发士大夫阿斯蒂芬阿斯蒂芬阿斯\"}', '{\"code\":200,\"msg\":\"新建成功\"}', '0', null, '2021-12-08 09:47:42');
INSERT INTO `sys_oper_log` VALUES ('221', '新增商铺', '1', 'com.zhaoxinms.baseconfig.controller.ConfigHouseController.create()', 'POST', '1', 'admin', null, '/baseconfig/House', '127.0.0.1', '内网IP', '{\"useSquare\":\"110\",\"code\":\"01-02\",\"buildingSquare\":\"122.20\",\"block\":\"0012\",\"remark\":\"的风格士大夫收费格式发给收费个首付大概士大夫公司发的告诉对方告诉对方敢死队风格岁的法国岁的法国岁的法国时代附属国豆腐干士大夫岁的法国岁的法国岁的法国岁的法国岁的法国岁的法国士大夫敢死队风格岁的法国岁的法国岁的法国岁的法国士大夫告诉对方敢死队风格岁的法国岁的法国士大夫告诉对方告诉对方告诉对方告诉对方敢死队风格顺丰大概士大夫公司法告诉对方敢死队风格岁的法国士大夫公司大锅饭时代告诉对方告诉对方公司的是德\",\"state\":\"empty\",\"floor\":\"1\",\"rentFee\":\"5000\"}', '{\"code\":200,\"msg\":\"新建成功\"}', '0', null, '2021-12-08 10:07:53');
INSERT INTO `sys_oper_log` VALUES ('222', '新增商铺', '1', 'com.zhaoxinms.baseconfig.controller.ConfigHouseController.create()', 'POST', '1', 'admin', null, '/baseconfig/House', '127.0.0.1', '内网IP', '{\"useSquare\":\"4545645132123.22\",\"code\":\"02-5173\",\"buildingSquare\":\"98989856564641362.21\",\"block\":\"0012\",\"state\":\"empty\",\"floor\":\"99\",\"rentFee\":\"5000\"}', null, '1', '\r\n### Error updating database.  Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Out of range value for column \'building_square\' at row 1\r\n### The error may exist in com/zhaoxinms/baseconfig/mapper/ConfigHouseMapper.java (best guess)\r\n### The error may involve com.zhaoxinms.baseconfig.mapper.ConfigHouseMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO config_house  ( ID, CODE, Name, BLOCK, RENT_FEE, FLOOR, STATE, BUILDING_SQUARE, USE_SQUARE,  CREATOR_TIME,     CREATOR_USER_ID, ENABLED_MARK )  VALUES  ( ?, ?, ?, ?, ?, ?, ?, ?, ?,  ?,     ?, ? )\r\n### Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Out of range value for column \'building_square\' at row 1\n; Data truncation: Out of range value for column \'building_square\' at row 1; nested exception is com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Out of range value for column \'building_square\' at row 1', '2021-12-08 10:12:24');
INSERT INTO `sys_oper_log` VALUES ('223', '新增商铺', '1', 'com.zhaoxinms.baseconfig.controller.ConfigHouseController.create()', 'POST', '1', 'admin', null, '/baseconfig/House', '127.0.0.1', '内网IP', '{\"useSquare\":\"4545645132123.22\",\"code\":\"02-5173\",\"buildingSquare\":\"98989856564641362.21\",\"block\":\"0012\",\"state\":\"empty\",\"floor\":\"99\",\"rentFee\":\"5000\"}', null, '1', '\r\n### Error updating database.  Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Out of range value for column \'building_square\' at row 1\r\n### The error may exist in com/zhaoxinms/baseconfig/mapper/ConfigHouseMapper.java (best guess)\r\n### The error may involve com.zhaoxinms.baseconfig.mapper.ConfigHouseMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO config_house  ( ID, CODE, Name, BLOCK, RENT_FEE, FLOOR, STATE, BUILDING_SQUARE, USE_SQUARE,  CREATOR_TIME,     CREATOR_USER_ID, ENABLED_MARK )  VALUES  ( ?, ?, ?, ?, ?, ?, ?, ?, ?,  ?,     ?, ? )\r\n### Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Out of range value for column \'building_square\' at row 1\n; Data truncation: Out of range value for column \'building_square\' at row 1; nested exception is com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Out of range value for column \'building_square\' at row 1', '2021-12-08 10:22:18');
INSERT INTO `sys_oper_log` VALUES ('224', '新增商铺', '1', 'com.zhaoxinms.baseconfig.controller.ConfigHouseController.create()', 'POST', '1', 'admin', null, '/baseconfig/House', '127.0.0.1', '内网IP', '{\"useSquare\":\"6532632653\",\"code\":\"01-023\",\"buildingSquare\":\"5656565665\",\"block\":\"0012\",\"state\":\"empty\",\"floor\":\"23\",\"rentFee\":\"5005050505\"}', null, '1', '\r\n### Error updating database.  Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Out of range value for column \'rent_fee\' at row 1\r\n### The error may exist in com/zhaoxinms/baseconfig/mapper/ConfigHouseMapper.java (best guess)\r\n### The error may involve com.zhaoxinms.baseconfig.mapper.ConfigHouseMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO config_house  ( ID, CODE, Name, BLOCK, RENT_FEE, FLOOR, STATE, BUILDING_SQUARE, USE_SQUARE,  CREATOR_TIME,     CREATOR_USER_ID, ENABLED_MARK )  VALUES  ( ?, ?, ?, ?, ?, ?, ?, ?, ?,  ?,     ?, ? )\r\n### Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Out of range value for column \'rent_fee\' at row 1\n; Data truncation: Out of range value for column \'rent_fee\' at row 1; nested exception is com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Out of range value for column \'rent_fee\' at row 1', '2021-12-08 10:23:07');
INSERT INTO `sys_oper_log` VALUES ('225', '新增商铺', '1', 'com.zhaoxinms.baseconfig.controller.ConfigHouseController.create()', 'POST', '1', 'admin', null, '/baseconfig/House', '127.0.0.1', '内网IP', '{\"useSquare\":\"6532632653\",\"code\":\"01-023\",\"buildingSquare\":\"5656565665\",\"block\":\"0012\",\"state\":\"empty\",\"floor\":\"23\",\"rentFee\":\"5005050505\"}', null, '1', '\r\n### Error updating database.  Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Out of range value for column \'rent_fee\' at row 1\r\n### The error may exist in com/zhaoxinms/baseconfig/mapper/ConfigHouseMapper.java (best guess)\r\n### The error may involve com.zhaoxinms.baseconfig.mapper.ConfigHouseMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO config_house  ( ID, CODE, Name, BLOCK, RENT_FEE, FLOOR, STATE, BUILDING_SQUARE, USE_SQUARE,  CREATOR_TIME,     CREATOR_USER_ID, ENABLED_MARK )  VALUES  ( ?, ?, ?, ?, ?, ?, ?, ?, ?,  ?,     ?, ? )\r\n### Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Out of range value for column \'rent_fee\' at row 1\n; Data truncation: Out of range value for column \'rent_fee\' at row 1; nested exception is com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Out of range value for column \'rent_fee\' at row 1', '2021-12-08 10:23:25');
INSERT INTO `sys_oper_log` VALUES ('226', '新增商铺', '1', 'com.zhaoxinms.baseconfig.controller.ConfigHouseController.create()', 'POST', '1', 'admin', null, '/baseconfig/House', '127.0.0.1', '内网IP', '{\"useSquare\":\"6532632653\",\"code\":\"01-023\",\"buildingSquare\":\"5656565665\",\"block\":\"0012\",\"state\":\"empty\",\"floor\":\"23\",\"rentFee\":\"50050505\"}', null, '1', '\r\n### Error updating database.  Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Out of range value for column \'building_square\' at row 1\r\n### The error may exist in com/zhaoxinms/baseconfig/mapper/ConfigHouseMapper.java (best guess)\r\n### The error may involve com.zhaoxinms.baseconfig.mapper.ConfigHouseMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO config_house  ( ID, CODE, Name, BLOCK, RENT_FEE, FLOOR, STATE, BUILDING_SQUARE, USE_SQUARE,  CREATOR_TIME,     CREATOR_USER_ID, ENABLED_MARK )  VALUES  ( ?, ?, ?, ?, ?, ?, ?, ?, ?,  ?,     ?, ? )\r\n### Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Out of range value for column \'building_square\' at row 1\n; Data truncation: Out of range value for column \'building_square\' at row 1; nested exception is com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Out of range value for column \'building_square\' at row 1', '2021-12-08 10:24:38');
INSERT INTO `sys_oper_log` VALUES ('227', '新增商铺', '1', 'com.zhaoxinms.baseconfig.controller.ConfigHouseController.create()', 'POST', '1', 'admin', null, '/baseconfig/House', '127.0.0.1', '内网IP', '{\"useSquare\":\"99999999\",\"code\":\"01-03\",\"buildingSquare\":\"99999999.9\",\"block\":\"0012\",\"state\":\"empty\",\"floor\":\"5\",\"rentFee\":\"9999999.99\"}', '{\"code\":200,\"msg\":\"新建成功\"}', '0', null, '2021-12-08 11:35:52');
INSERT INTO `sys_oper_log` VALUES ('228', '商业区删除', '3', 'com.zhaoxinms.baseconfig.controller.ConfigHouseBlockController.delete()', 'DELETE', '1', 'admin', null, '/baseconfig/ConfigHouseBlock/1468396838980718594', '127.0.0.1', '内网IP', '{id=1468396838980718594}', '{\"code\":200,\"msg\":\"删除成功\"}', '0', null, '2021-12-08 11:36:09');
INSERT INTO `sys_oper_log` VALUES ('229', '新增收费项', '1', 'com.zhaoxinms.baseconfig.controller.ConfigFeeItemController.create()', 'POST', '1', 'admin', null, '/baseconfig/ConfigFeeItem', '127.0.0.1', '内网IP', '{\"lateFeeDays\":60,\"name\":\"12345678912345678912345678912345678912345678912345\",\"formula\":\"\",\"numType\":\"\",\"type\":\"temp\",\"lateFeeEnable\":0}', '{\"code\":200,\"msg\":\"新建成功\"}', '0', null, '2021-12-08 11:38:42');
INSERT INTO `sys_oper_log` VALUES ('230', '商业区创建', '1', 'com.zhaoxinms.baseconfig.controller.ConfigHouseBlockController.create()', 'POST', '1', 'admin', null, '/baseconfig/ConfigHouseBlock', '127.0.0.1', '内网IP', '{\"code\":\"01\",\"name\":\"重复名\"}', null, '1', '该编号已经存在，创建失败', '2021-12-08 11:39:32');
INSERT INTO `sys_oper_log` VALUES ('231', '商业区创建', '1', 'com.zhaoxinms.baseconfig.controller.ConfigHouseBlockController.create()', 'POST', '1', 'admin', null, '/baseconfig/ConfigHouseBlock', '127.0.0.1', '内网IP', '{\"code\":\"012\",\"name\":\"重复名\"}', '{\"code\":200,\"msg\":\"新建成功\"}', '0', null, '2021-12-08 11:39:36');
INSERT INTO `sys_oper_log` VALUES ('232', '商业区创建', '1', 'com.zhaoxinms.baseconfig.controller.ConfigHouseBlockController.create()', 'POST', '1', 'admin', null, '/baseconfig/ConfigHouseBlock', '127.0.0.1', '内网IP', '{\"code\":\"013\",\"name\":\"重复名\"}', null, '1', '\r\n### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'重复名\' for key \'house_unique_index\'\r\n### The error may exist in com/zhaoxinms/baseconfig/mapper/ConfigHouseBlockMapper.java (best guess)\r\n### The error may involve com.zhaoxinms.baseconfig.mapper.ConfigHouseBlockMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO config_house_block  ( F_ID, CODE, NAME,  ENABLED_MARK, CREATOR_USER_ID, CREATOR_TIME )  VALUES  ( ?, ?, ?,  ?, ?, ? )\r\n### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'重复名\' for key \'house_unique_index\'\n; Duplicate entry \'重复名\' for key \'house_unique_index\'; nested exception is java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'重复名\' for key \'house_unique_index\'', '2021-12-08 11:39:46');
INSERT INTO `sys_oper_log` VALUES ('233', '商业区创建', '1', 'com.zhaoxinms.baseconfig.controller.ConfigHouseBlockController.create()', 'POST', '1', 'admin', null, '/baseconfig/ConfigHouseBlock', '127.0.0.1', '内网IP', '{\"code\":\"013\",\"name\":\"重复名\"}', null, '1', '该商业区名已经存在，创建失败', '2021-12-08 11:43:16');
INSERT INTO `sys_oper_log` VALUES ('234', '商业区创建', '1', 'com.zhaoxinms.baseconfig.controller.ConfigHouseBlockController.create()', 'POST', '1', 'admin', null, '/baseconfig/ConfigHouseBlock', '127.0.0.1', '内网IP', '{\"code\":\"321\",\"name\":\"重复名\"}', null, '1', '该商业区名已经存在，创建失败', '2021-12-08 11:43:40');
INSERT INTO `sys_oper_log` VALUES ('235', '新增收费项', '1', 'com.zhaoxinms.baseconfig.controller.ConfigFeeItemController.create()', 'POST', '1', 'admin', null, '/baseconfig/ConfigFeeItem', '127.0.0.1', '内网IP', '{\"lateFeeDays\":60,\"name\":\"公共区域电费\",\"formula\":\"\",\"numType\":\"\",\"type\":\"temp\",\"lateFeeEnable\":0}', null, '1', '该收费项已存在，创建失败', '2021-12-08 11:43:58');
INSERT INTO `sys_oper_log` VALUES ('236', '新增收费项', '1', 'com.zhaoxinms.baseconfig.controller.ConfigFeeItemController.create()', 'POST', '1', 'admin', null, '/baseconfig/ConfigFeeItem', '127.0.0.1', '内网IP', '{\"lateFeeDays\":60,\"period\":1,\"price\":9999999,\"name\":\"公共区域电费1\",\"formula\":\"base\",\"lateFeeRate\":\"0.1\",\"numType\":\"flat\",\"type\":\"house\",\"lateFeeEnable\":1}', null, '1', '\r\n### Error updating database.  Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Out of range value for column \'price\' at row 1\r\n### The error may exist in com/zhaoxinms/baseconfig/mapper/ConfigFeeItemMapper.java (best guess)\r\n### The error may involve com.zhaoxinms.baseconfig.mapper.ConfigFeeItemMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO config_fee_item  ( ID, TYPE, NAME, PRICE, NUM_TYPE, PERIOD, FORMULA, FORMULA_EXPRESSION, GENERATE_TYPE, LATE_FEE_ENABLE, LATE_FEE_DAYS, LATE_FEE_RATE, CREATOR_TIME, CREATOR_USER_ID,     ENABLED_MARK )  VALUES  ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,     ? )\r\n### Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Out of range value for column \'price\' at row 1\n; Data truncation: Out of range value for column \'price\' at row 1; nested exception is com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Out of range value for column \'price\' at row 1', '2021-12-08 11:45:44');
INSERT INTO `sys_oper_log` VALUES ('237', '新增收费项', '1', 'com.zhaoxinms.baseconfig.controller.ConfigFeeItemController.create()', 'POST', '1', 'admin', null, '/baseconfig/ConfigFeeItem', '127.0.0.1', '内网IP', '{\"lateFeeDays\":60,\"period\":6,\"price\":9999999,\"name\":\"sdfsdf\",\"formula\":\"base\",\"numType\":\"people\",\"type\":\"house\",\"lateFeeEnable\":0}', null, '1', '\r\n### Error updating database.  Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Out of range value for column \'price\' at row 1\r\n### The error may exist in com/zhaoxinms/baseconfig/mapper/ConfigFeeItemMapper.java (best guess)\r\n### The error may involve com.zhaoxinms.baseconfig.mapper.ConfigFeeItemMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO config_fee_item  ( ID, TYPE, NAME, PRICE, NUM_TYPE, PERIOD, FORMULA, FORMULA_EXPRESSION, GENERATE_TYPE, LATE_FEE_ENABLE, LATE_FEE_DAYS, LATE_FEE_RATE, CREATOR_TIME, CREATOR_USER_ID,     ENABLED_MARK )  VALUES  ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,     ? )\r\n### Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Out of range value for column \'price\' at row 1\n; Data truncation: Out of range value for column \'price\' at row 1; nested exception is com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Out of range value for column \'price\' at row 1', '2021-12-08 11:50:06');
INSERT INTO `sys_oper_log` VALUES ('238', '新增收费项', '1', 'com.zhaoxinms.baseconfig.controller.ConfigFeeItemController.create()', 'POST', '1', 'admin', null, '/baseconfig/ConfigFeeItem', '127.0.0.1', '内网IP', '{\"lateFeeDays\":60,\"period\":6,\"price\":9999999,\"name\":\"sdfsdf\",\"formula\":\"base\",\"numType\":\"people\",\"type\":\"house\",\"lateFeeEnable\":0}', null, '1', '\r\n### Error updating database.  Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Out of range value for column \'price\' at row 1\r\n### The error may exist in com/zhaoxinms/baseconfig/mapper/ConfigFeeItemMapper.java (best guess)\r\n### The error may involve com.zhaoxinms.baseconfig.mapper.ConfigFeeItemMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO config_fee_item  ( ID, TYPE, NAME, PRICE, NUM_TYPE, PERIOD, FORMULA, FORMULA_EXPRESSION, GENERATE_TYPE, LATE_FEE_ENABLE, LATE_FEE_DAYS, LATE_FEE_RATE, CREATOR_TIME, CREATOR_USER_ID,     ENABLED_MARK )  VALUES  ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,     ? )\r\n### Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Out of range value for column \'price\' at row 1\n; Data truncation: Out of range value for column \'price\' at row 1; nested exception is com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Out of range value for column \'price\' at row 1', '2021-12-08 11:50:19');
INSERT INTO `sys_oper_log` VALUES ('239', '收费数据创建', '1', 'com.zhaoxinms.payment.controller.PaymentBillCreateController.create()', 'POST', '1', 'admin', null, '/payment/PaymentBillCreate', '127.0.0.1', '内网IP', '{\"feeItemId\":\"1456546648464388097\",\"resourceId\":\"1438429781237432321\",\"endDate\":1639584000000,\"num\":\"0.00\",\"multiple\":1,\"resourceName\":\"01-002\",\"currentIndex\":9999999999999,\"beginDate\":1638892800000,\"loss\":0,\"total\":\"0.00\",\"price\":\"98656232323.0000\",\"lastIndex\":9999999999999,\"deadline\":1640188800000,\"feeUser\":\"赵东来\"}', null, '1', '数量必须大于0', '2021-12-08 15:58:59');
INSERT INTO `sys_oper_log` VALUES ('240', '收费数据创建', '1', 'com.zhaoxinms.payment.controller.PaymentBillCreateController.create()', 'POST', '1', 'admin', null, '/payment/PaymentBillCreate', '127.0.0.1', '内网IP', '{\"feeItemId\":\"1456546648464388097\",\"resourceId\":\"1438429781237432321\",\"endDate\":1639584000000,\"num\":\"2.00\",\"multiple\":1,\"resourceName\":\"01-002\",\"currentIndex\":99999999999999,\"beginDate\":1638892800000,\"loss\":0,\"total\":\"1972646.00\",\"price\":\"986323.0000\",\"lastIndex\":9999999999999,\"deadline\":1640188800000,\"feeUser\":\"赵东来\"}', null, '1', '\r\n### Error updating database.  Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Out of range value for column \'last_index\' at row 1\r\n### The error may exist in com/zhaoxinms/payment/mapper/PaymentBillMapper.java (best guess)\r\n### The error may involve com.zhaoxinms.payment.mapper.PaymentBillMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO payment_bill  ( ID, TYPE, RESOURCE_NAME, RESOURCE_ID, CONTRACT_ID, FEE_ITEM_ID, FEE_ITEM_NAME, FEE_USER, BEGIN_DATE, END_DATE, deadline, LAST_INDEX, CURRENT_INDEX, MULTIPLE, LOSS, NUM, PRICE, TOTAL,        CREATOR_USER_ID, CREATOR_TIME,     ENABLED_MARK )  VALUES  ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,        ?, ?,     ? )\r\n### Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Out of range value for column \'last_index\' at row 1\n; Data truncation: Out of range value for column \'last_index\' at row 1; nested exception is com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Out of range value for column \'last_index\' at row 1', '2021-12-08 15:59:29');
INSERT INTO `sys_oper_log` VALUES ('241', '收费数据创建', '1', 'com.zhaoxinms.payment.controller.PaymentBillCreateController.create()', 'POST', '1', 'admin', null, '/payment/PaymentBillCreate', '127.0.0.1', '内网IP', '{\"feeItemId\":\"1456546648464388097\",\"resourceId\":\"1438429781237432321\",\"endDate\":1639584000000,\"num\":\"2.00\",\"multiple\":1,\"resourceName\":\"01-002\",\"currentIndex\":99999999999999,\"beginDate\":1638892800000,\"loss\":0,\"total\":\"1972646.00\",\"price\":\"986323.0000\",\"lastIndex\":9999999999999,\"deadline\":1640188800000,\"feeUser\":\"赵东来\"}', null, '1', '\r\n### Error updating database.  Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Out of range value for column \'last_index\' at row 1\r\n### The error may exist in com/zhaoxinms/payment/mapper/PaymentBillMapper.java (best guess)\r\n### The error may involve com.zhaoxinms.payment.mapper.PaymentBillMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO payment_bill  ( ID, TYPE, RESOURCE_NAME, RESOURCE_ID, CONTRACT_ID, FEE_ITEM_ID, FEE_ITEM_NAME, FEE_USER, BEGIN_DATE, END_DATE, deadline, LAST_INDEX, CURRENT_INDEX, MULTIPLE, LOSS, NUM, PRICE, TOTAL,        CREATOR_USER_ID, CREATOR_TIME,     ENABLED_MARK )  VALUES  ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,        ?, ?,     ? )\r\n### Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Out of range value for column \'last_index\' at row 1\n; Data truncation: Out of range value for column \'last_index\' at row 1; nested exception is com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Out of range value for column \'last_index\' at row 1', '2021-12-08 16:05:06');
INSERT INTO `sys_oper_log` VALUES ('242', '收费数据创建', '1', 'com.zhaoxinms.payment.controller.PaymentBillCreateController.create()', 'POST', '1', 'admin', null, '/payment/PaymentBillCreate', '127.0.0.1', '内网IP', '{\"feeItemId\":\"1456546648464388097\",\"resourceId\":\"1438429781237432321\",\"endDate\":1639584000000,\"num\":\"2000\",\"multiple\":1,\"resourceName\":\"01-002\",\"currentIndex\":0,\"beginDate\":1638288000000,\"loss\":0,\"total\":\"17318000.00\",\"price\":\"8659.0000\",\"lastIndex\":0,\"deadline\":1640188800000,\"feeUser\":\"赵东来\"}', '{\"code\":200,\"msg\":\"新建成功\"}', '0', null, '2021-12-08 16:46:15');
INSERT INTO `sys_oper_log` VALUES ('243', '收费数据批量创建', '1', 'com.zhaoxinms.payment.controller.PaymentBillCreateController.batchCreate()', 'POST', '1', 'admin', null, '/payment/PaymentBillCreate/batchCreate', '127.0.0.1', '内网IP', '{\"feeItemId\":\"1456546648464388097\",\"beginDate\":1638892800000,\"endDate\":1639065600000,\"price\":\"32.0000\",\"resourceName\":\"01-002,01-004,01-005\",\"deadline\":1639497600000}', '{\"code\":200,\"msg\":\"创建数据成功\"}', '0', null, '2021-12-08 16:55:18');
INSERT INTO `sys_oper_log` VALUES ('244', '商业区更新', '2', 'com.zhaoxinms.baseconfig.controller.ConfigHouseBlockController.update()', 'PUT', '1', 'admin', null, '/baseconfig/ConfigHouseBlock/f76112311fba4961aa4326f3be0005bc', '127.0.0.1', '内网IP', 'f76112311fba4961aa4326f3be0005bc {\"code\":\"01\",\"name\":\"中心区商业街\",\"id\":\"f76112311fba4961aa4326f3be0005bc\"}', '{\"code\":200,\"msg\":\"更新成功\"}', '0', null, '2021-12-08 17:30:30');
INSERT INTO `sys_oper_log` VALUES ('245', '商业区更新', '2', 'com.zhaoxinms.baseconfig.controller.ConfigHouseBlockController.update()', 'PUT', '1', 'admin', null, '/baseconfig/ConfigHouseBlock/1468424998715768834', '127.0.0.1', '内网IP', '1468424998715768834 {\"code\":\"012\",\"name\":\"瀚海大道商业街\",\"id\":\"1468424998715768834\"}', '{\"code\":200,\"msg\":\"更新成功\"}', '0', null, '2021-12-08 17:30:53');
INSERT INTO `sys_oper_log` VALUES ('246', '创建租售合约', '1', 'com.zhaoxinms.payment.controller.PaymentContractController.create()', 'POST', '1', 'admin', null, '/payment/PaymentContract', '127.0.0.1', '内网IP', '{\"blockCode\":\"01\",\"period\":0,\"resourceId\":\"1438429781237432326\",\"contractType\":\"selled\",\"userPhone\":\"13671354640\",\"userName\":\"sdfdfsdfsdfsdfsdfsds\",\"beginDate\":1638806400000,\"userTradeDetail\":\"321231321\",\"userTrade\":\"27c38cf2-dc0a-4449-ab87-7eb68f7e425b\",\"userGender\":\"00027e3ecaf54f008a5d7e25c6d4f4c3\",\"userIdcard\":\"140702198701087056\",\"contractFees\":[{\"feeItemId\":\"6d81c6bc842e4d8ebfdfbd653b5e1f64\",\"price\":\"66.0000\",\"name\":\"物业管理费\",\"id\":\"\"}],\"resourceType\":\"house\"}', '{\"code\":200,\"msg\":\"新建成功\"}', '0', null, '2021-12-08 18:04:36');
INSERT INTO `sys_oper_log` VALUES ('247', '商铺导入', '6', 'com.zhaoxinms.baseconfig.controller.ConfigHouseImportController.importPreview()', 'GET', '1', 'admin', null, '/baseconfig/HouseImport/Import', '127.0.0.1', '内网IP', '{}', null, '1', '', '2021-12-08 19:33:22');
INSERT INTO `sys_oper_log` VALUES ('248', '商铺导入', '6', 'com.zhaoxinms.baseconfig.controller.ConfigHouseImportController.importPreview()', 'GET', '1', 'admin', null, '/baseconfig/HouseImport/Import', '127.0.0.1', '内网IP', '{}', null, '1', 'com.zhaoxinms.baseconfig.mapper.ConfigHouseMapper.insert (batch index #1) failed. Cause: java.sql.BatchUpdateException: Data truncation: Out of range value for column \'rent_fee\' at row 1\n; Data truncation: Out of range value for column \'rent_fee\' at row 1; nested exception is java.sql.BatchUpdateException: Data truncation: Out of range value for column \'rent_fee\' at row 1', '2021-12-08 19:46:42');
INSERT INTO `sys_oper_log` VALUES ('249', '商铺导入', '6', 'com.zhaoxinms.baseconfig.controller.ConfigHouseImportController.importPreview()', 'GET', '1', 'admin', null, '/baseconfig/HouseImport/Import', '127.0.0.1', '内网IP', '{}', null, '1', '导入数据失败：第1条数据格式不正确', '2021-12-08 19:53:14');
INSERT INTO `sys_oper_log` VALUES ('250', '商铺导入', '6', 'com.zhaoxinms.baseconfig.controller.ConfigHouseImportController.importPreview()', 'GET', '1', 'admin', null, '/baseconfig/HouseImport/Import', '127.0.0.1', '内网IP', '{}', null, '1', '导入数据失败：第1条数据格式不正确', '2021-12-08 19:58:30');
INSERT INTO `sys_oper_log` VALUES ('251', '商铺导入', '6', 'com.zhaoxinms.baseconfig.controller.ConfigHouseImportController.importPreview()', 'GET', '1', 'admin', null, '/baseconfig/HouseImport/Import', '127.0.0.1', '内网IP', '{}', '{\"code\":200,\"data\":{\"num\":\"1\"},\"msg\":\"Success\"}', '0', null, '2021-12-08 19:59:19');
INSERT INTO `sys_oper_log` VALUES ('252', '抄表数据导入', '6', 'com.zhaoxinms.payment.controller.PaymentMeterImportController.importPreview()', 'GET', '1', 'admin', null, '/payment/PaymentMeterImport/Import', '127.0.0.1', '内网IP', '{}', null, '1', '校验第1条数据失败，商铺信息有误', '2021-12-08 20:25:34');
INSERT INTO `sys_oper_log` VALUES ('253', '抄表数据导入', '6', 'com.zhaoxinms.payment.controller.PaymentMeterImportController.importPreview()', 'GET', '1', 'admin', null, '/payment/PaymentMeterImport/Import', '127.0.0.1', '内网IP', '{}', '{\"code\":200,\"data\":{\"num\":\"1\"},\"msg\":\"Success\"}', '0', null, '2021-12-08 20:30:59');
INSERT INTO `sys_oper_log` VALUES ('254', '临时收费', '1', 'com.zhaoxinms.payment.controller.PaymentTempController.create()', 'POST', '1', 'admin', null, '/payment/PaymentTemp', '127.0.0.1', '内网IP', '{\"feeItemId\":\"1456546272025604098\",\"resourceId\":\"1438429781237432321\",\"payType\":\"9d46e7f3729843ef8dc35f9987f7f2fe\",\"operateTime\":1638967885639,\"amt\":23563221.22,\"block\":\"01\",\"resourceName\":\"01-002\",\"feeUser\":\"赵东来\"}', '{\"code\":200,\"msg\":\"新增费用成功\"}', '0', null, '2021-12-08 20:54:23');
INSERT INTO `sys_oper_log` VALUES ('255', '临时收费', '1', 'com.zhaoxinms.payment.controller.PaymentTempController.create()', 'POST', '1', 'admin', null, '/payment/PaymentTemp', '127.0.0.1', '内网IP', '{\"feeItemId\":\"1456546272025604098\",\"resourceId\":\"1438429781237432321\",\"payType\":\"9d46e7f3729843ef8dc35f9987f7f2fe\",\"operateTime\":1638968066154,\"amt\":32154632,\"block\":\"01\",\"resourceName\":\"01-002\",\"feeUser\":\"赵东来\"}', '{\"code\":200,\"msg\":\"新增费用成功\"}', '0', null, '2021-12-08 20:54:56');
INSERT INTO `sys_oper_log` VALUES ('256', '新增收费项', '1', 'com.zhaoxinms.baseconfig.controller.ConfigFeeItemController.create()', 'POST', '1', 'admin', null, '/baseconfig/ConfigFeeItem', '127.0.0.1', '内网IP', '{\"lateFeeDays\":60,\"period\":1,\"price\":0.45,\"name\":\"抄表水费\",\"formula\":\"base\",\"numType\":\"meter\",\"type\":\"house\",\"lateFeeEnable\":0}', '{\"code\":200,\"msg\":\"新建成功\"}', '0', null, '2020-12-03 07:54:16');
INSERT INTO `sys_oper_log` VALUES ('257', '删除收费项', '3', 'com.zhaoxinms.baseconfig.controller.ConfigFeeItemController.delete()', 'DELETE', '1', 'admin', null, '/baseconfig/ConfigFeeItem/1468424773234180097', '127.0.0.1', '内网IP', '{id=1468424773234180097}', '{\"code\":200,\"msg\":\"删除成功\"}', '0', null, '2020-12-03 07:54:22');
INSERT INTO `sys_oper_log` VALUES ('258', '新增收费项', '1', 'com.zhaoxinms.baseconfig.controller.ConfigFeeItemController.create()', 'POST', '1', 'admin', null, '/baseconfig/ConfigFeeItem', '127.0.0.1', '内网IP', '{\"lateFeeDays\":60,\"name\":\"十分士大夫士大夫大师傅发射点发射点发\",\"formula\":\"\",\"numType\":\"\",\"type\":\"temp\",\"lateFeeEnable\":0}', '{\"code\":200,\"msg\":\"新建成功\"}', '0', null, '2020-12-03 07:54:50');
INSERT INTO `sys_oper_log` VALUES ('259', '删除收费项', '3', 'com.zhaoxinms.baseconfig.controller.ConfigFeeItemController.delete()', 'DELETE', '1', 'admin', null, '/baseconfig/ConfigFeeItem/1468424773234180097', '127.0.0.1', '内网IP', '{id=1468424773234180097}', '{\"code\":200,\"msg\":\"删除成功\"}', '0', null, '2020-12-03 07:54:58');
INSERT INTO `sys_oper_log` VALUES ('260', '商铺导入', '6', 'com.zhaoxinms.baseconfig.controller.ConfigHouseImportController.importPreview()', 'GET', '1', 'admin', null, '/baseconfig/HouseImport/Import', '127.0.0.1', '内网IP', '{}', null, '1', '导入数据失败：第1条数据格式不正确', '2021-12-03 11:07:28');
INSERT INTO `sys_oper_log` VALUES ('261', '新增商铺', '1', 'com.zhaoxinms.baseconfig.controller.ConfigHouseController.create()', 'POST', '1', 'admin', null, '/baseconfig/House', '127.0.0.1', '内网IP', '{\"useSquare\":\"160\",\"code\":\"001\",\"buildingSquare\":\"200\",\"block\":\"01\",\"state\":\"empty\",\"floor\":\"1\",\"rentFee\":\"120000\"}', '{\"code\":200,\"msg\":\"新建成功\"}', '0', null, '2021-12-03 11:12:42');
INSERT INTO `sys_oper_log` VALUES ('262', '商铺导入', '6', 'com.zhaoxinms.baseconfig.controller.ConfigHouseImportController.importPreview()', 'GET', '1', 'admin', null, '/baseconfig/HouseImport/Import', '127.0.0.1', '内网IP', '{}', null, '1', '导入数据失败：第1条数据格式不正确', '2021-12-03 11:13:14');
INSERT INTO `sys_oper_log` VALUES ('263', '商铺导入', '6', 'com.zhaoxinms.baseconfig.controller.ConfigHouseImportController.importPreview()', 'GET', '1', 'admin', null, '/baseconfig/HouseImport/Import', '127.0.0.1', '内网IP', '{}', null, '1', '导入数据失败：第1条数据格式不正确', '2021-12-03 11:15:45');
INSERT INTO `sys_oper_log` VALUES ('264', '商铺导入', '6', 'com.zhaoxinms.baseconfig.controller.ConfigHouseImportController.importPreview()', 'GET', '1', 'admin', null, '/baseconfig/HouseImport/Import', '127.0.0.1', '内网IP', '{}', null, '1', '导入数据失败：第1条数据格式不正确', '2021-12-03 11:16:44');
INSERT INTO `sys_oper_log` VALUES ('265', '商铺导入', '6', 'com.zhaoxinms.baseconfig.controller.ConfigHouseImportController.importPreview()', 'GET', '1', 'admin', null, '/baseconfig/HouseImport/Import', '127.0.0.1', '内网IP', '{}', '{\"code\":200,\"data\":{\"num\":\"30\"},\"msg\":\"Success\"}', '0', null, '2021-12-03 11:17:28');
INSERT INTO `sys_oper_log` VALUES ('266', '删除收费项', '3', 'com.zhaoxinms.baseconfig.controller.ConfigFeeItemController.delete()', 'DELETE', '1', 'admin', null, '/baseconfig/ConfigFeeItem/1334284923695046657', '127.0.0.1', '内网IP', '{id=1334284923695046657}', '{\"code\":200,\"msg\":\"删除成功\"}', '0', null, '2020-12-03 13:39:54');
INSERT INTO `sys_oper_log` VALUES ('267', '新增收费项', '1', 'com.zhaoxinms.baseconfig.controller.ConfigFeeItemController.create()', 'POST', '1', 'admin', null, '/baseconfig/ConfigFeeItem', '127.0.0.1', '内网IP', '{\"lateFeeDays\":60,\"name\":\"装修垃圾清运费\",\"formula\":\"\",\"numType\":\"\",\"type\":\"temp\",\"lateFeeEnable\":0}', null, '1', '该收费项已存在，创建失败', '2020-12-03 13:42:46');
INSERT INTO `sys_oper_log` VALUES ('268', '更新收费项', '2', 'com.zhaoxinms.baseconfig.controller.ConfigFeeItemController.update()', 'PUT', '1', 'admin', null, '/baseconfig/ConfigFeeItem/1456545461837066241', '127.0.0.1', '内网IP', '1456545461837066241 {\"lateFeeDays\":0,\"period\":12,\"formulaExpression\":\"\",\"price\":0,\"name\":\"装修垃圾清运费\",\"formula\":\"base\",\"id\":\"1456545461837066241\",\"lateFeeRate\":\"0\",\"numType\":\"flat\",\"type\":\"temp\",\"lateFeeEnable\":0}', '{\"code\":200,\"msg\":\"更新成功\"}', '0', null, '2020-12-03 13:43:14');
INSERT INTO `sys_oper_log` VALUES ('269', '更新收费项', '2', 'com.zhaoxinms.baseconfig.controller.ConfigFeeItemController.update()', 'PUT', '1', 'admin', null, '/baseconfig/ConfigFeeItem/1456545687880691714', '127.0.0.1', '内网IP', '1456545687880691714 {\"lateFeeDays\":0,\"period\":1,\"formulaExpression\":\"\",\"price\":0,\"name\":\"装修违章罚款\",\"formula\":\"base\",\"id\":\"1456545687880691714\",\"lateFeeRate\":\"0\",\"numType\":\"flat\",\"type\":\"temp\",\"lateFeeEnable\":0}', '{\"code\":200,\"msg\":\"更新成功\"}', '0', null, '2020-12-03 13:48:07');
INSERT INTO `sys_oper_log` VALUES ('270', '更新收费项', '2', 'com.zhaoxinms.baseconfig.controller.ConfigFeeItemController.update()', 'PUT', '1', 'admin', null, '/baseconfig/ConfigFeeItem/1456545945662615553', '127.0.0.1', '内网IP', '1456545945662615553 {\"lateFeeDays\":0,\"period\":1,\"formulaExpression\":\"\",\"price\":0,\"name\":\"物业违章罚款\",\"formula\":\"base\",\"id\":\"1456545945662615553\",\"lateFeeRate\":\"0\",\"numType\":\"flat\",\"type\":\"temp\",\"lateFeeEnable\":0}', '{\"code\":200,\"msg\":\"更新成功\"}', '0', null, '2020-12-03 13:49:32');
INSERT INTO `sys_oper_log` VALUES ('271', '创建租售合约', '1', 'com.zhaoxinms.payment.controller.PaymentContractController.create()', 'POST', '1', 'admin', null, '/payment/PaymentContract', '127.0.0.1', '内网IP', '{\"blockCode\":\"01\",\"period\":0,\"resourceId\":\"1466607490492850177\",\"contractType\":\"selled\",\"userPhone\":\"13654123658\",\"userName\":\"张一\",\"rentFee\":\"120000.00\",\"beginDate\":1638460800000,\"userTrade\":\"0fcc751d-9ceb-4767-8755-f0189b239468\",\"userGender\":\"84ff10b322d74ac3a653e3176724f909\",\"userIdcard\":\"140702198801087001\",\"contractFees\":[{\"feeItemId\":\"6d81c6bc842e4d8ebfdfbd653b5e1f64\",\"price\":\"66.0000\",\"name\":\"物业管理费\",\"id\":\"\"},{\"feeItemId\":\"1334284783299108865\",\"price\":\"0.4500\",\"name\":\"抄表水费\",\"id\":\"\"}],\"resourceType\":\"house\"}', '{\"code\":200,\"msg\":\"新建成功\"}', '0', null, '2021-12-03 14:21:23');
INSERT INTO `sys_oper_log` VALUES ('272', '创建租售合约', '1', 'com.zhaoxinms.payment.controller.PaymentContractController.create()', 'POST', '1', 'admin', null, '/payment/PaymentContract', '127.0.0.1', '内网IP', '{\"blockCode\":\"01\",\"period\":60,\"resourceId\":\"1466607490526404610\",\"contractType\":\"rented\",\"userPhone\":\"13265985632\",\"userName\":\"张二\",\"rentFee\":\"150000.00\",\"beginDate\":1638460800000,\"userTrade\":\"0fcc751d-9ceb-4767-8755-f0189b239468\",\"userGender\":\"84ff10b322d74ac3a653e3176724f909\",\"userIdcard\":\"140702198801087002\",\"contractFees\":[{\"feeItemId\":\"1334284783299108865\",\"beginDate\":1638460800000,\"price\":\"0.4500\",\"name\":\"抄表水费\",\"id\":\"\"},{\"feeItemId\":\"6d81c6bc842e4d8ebfdfbd653b5e1f64\",\"beginDate\":1638460800000,\"price\":\"66.0000\",\"name\":\"物业管理费\",\"id\":\"\"},{\"feeItemId\":\"99bcebcea39444bca429a18a413aabdd\",\"beginDate\":1638460800000,\"price\":\"1.0000\",\"name\":\"房屋租金\",\"id\":\"\"}],\"resourceType\":\"house\"}', '{\"code\":200,\"msg\":\"新建成功\"}', '0', null, '2021-12-03 14:22:48');
INSERT INTO `sys_oper_log` VALUES ('273', '创建租售合约', '1', 'com.zhaoxinms.payment.controller.PaymentContractController.create()', 'POST', '1', 'admin', null, '/payment/PaymentContract', '127.0.0.1', '内网IP', '{\"blockCode\":\"01\",\"period\":60,\"resourceId\":\"1466607490547376129\",\"contractType\":\"selled\",\"userPhone\":\"13652365258\",\"userName\":\"张三\",\"rentFee\":\"180000.00\",\"beginDate\":1639756800000,\"userTrade\":\"0fcc751d-9ceb-4767-8755-f0189b239468\",\"userGender\":\"84ff10b322d74ac3a653e3176724f909\",\"userIdcard\":\"140702198801087003\",\"contractFees\":[{\"feeItemId\":\"6d81c6bc842e4d8ebfdfbd653b5e1f64\",\"price\":\"66.0000\",\"name\":\"物业管理费\",\"id\":\"\"},{\"feeItemId\":\"1334284783299108865\",\"price\":\"0.4500\",\"name\":\"抄表水费\",\"id\":\"\"}],\"resourceType\":\"house\"}', '{\"code\":200,\"msg\":\"新建成功\"}', '0', null, '2021-12-03 14:23:54');
INSERT INTO `sys_oper_log` VALUES ('274', '删除租售合约', '3', 'com.zhaoxinms.payment.controller.PaymentContractController.delete()', 'DELETE', '1', 'admin', null, '/payment/PaymentContract/1466607490547376129', '127.0.0.1', '内网IP', '{houseid=1466607490547376129}', '{\"code\":200,\"msg\":\"收回商铺成功\"}', '0', null, '2021-12-03 14:27:12');
INSERT INTO `sys_oper_log` VALUES ('275', '删除租售合约', '3', 'com.zhaoxinms.payment.controller.PaymentContractController.delete()', 'DELETE', '1', 'admin', null, '/payment/PaymentContract/1466607490526404610', '127.0.0.1', '内网IP', '{houseid=1466607490526404610}', '{\"code\":200,\"msg\":\"收回商铺成功\"}', '0', null, '2021-12-03 14:27:18');
INSERT INTO `sys_oper_log` VALUES ('276', '删除租售合约', '3', 'com.zhaoxinms.payment.controller.PaymentContractController.delete()', 'DELETE', '1', 'admin', null, '/payment/PaymentContract/1466607490492850177', '127.0.0.1', '内网IP', '{houseid=1466607490492850177}', '{\"code\":200,\"msg\":\"收回商铺成功\"}', '0', null, '2021-12-03 15:10:41');
INSERT INTO `sys_oper_log` VALUES ('277', '创建租售合约', '1', 'com.zhaoxinms.payment.controller.PaymentContractController.create()', 'POST', '1', 'admin', null, '/payment/PaymentContract', '127.0.0.1', '内网IP', '{\"blockCode\":\"01\",\"period\":0,\"resourceId\":\"1466607490492850177\",\"contractType\":\"selled\",\"userPhone\":\"13684562365\",\"userName\":\"张一\",\"rentFee\":\"120000.00\",\"beginDate\":1606924800000,\"userTrade\":\"0fcc751d-9ceb-4767-8755-f0189b239468\",\"userGender\":\"84ff10b322d74ac3a653e3176724f909\",\"userIdcard\":\"140702198801087001\",\"contractFees\":[{\"feeItemId\":\"6d81c6bc842e4d8ebfdfbd653b5e1f64\",\"price\":\"66.0000\",\"name\":\"物业管理费\",\"id\":\"\"},{\"feeItemId\":\"1334284783299108865\",\"price\":\"0.4500\",\"name\":\"抄表水费\",\"id\":\"\"}],\"resourceType\":\"house\"}', null, '1', 'nested exception is org.apache.ibatis.exceptions.PersistenceException: \r\n### Error updating database.  Cause: java.lang.RuntimeException: Clock moved backwards.  Refusing to generate id for 31533112256 milliseconds\r\n### The error may exist in com/zhaoxinms/payment/mapper/PaymentContractMapper.java (best guess)\r\n### The error may involve com.zhaoxinms.payment.mapper.PaymentContractMapper.insert\r\n### The error occurred while executing an update\r\n### Cause: java.lang.RuntimeException: Clock moved backwards.  Refusing to generate id for 31533112256 milliseconds', '2020-12-03 15:12:01');
INSERT INTO `sys_oper_log` VALUES ('278', '创建租售合约', '1', 'com.zhaoxinms.payment.controller.PaymentContractController.create()', 'POST', '1', 'admin', null, '/payment/PaymentContract', '127.0.0.1', '内网IP', '{\"blockCode\":\"01\",\"period\":0,\"resourceId\":\"1466607490492850177\",\"contractType\":\"selled\",\"userPhone\":\"13265412321\",\"userName\":\"张一\",\"rentFee\":\"120000.00\",\"beginDate\":1625297773000,\"userTrade\":\"0fcc751d-9ceb-4767-8755-f0189b239468\",\"userGender\":\"84ff10b322d74ac3a653e3176724f909\",\"userIdcard\":\"140702198502036501\",\"contractFees\":[{\"feeItemId\":\"6d81c6bc842e4d8ebfdfbd653b5e1f64\",\"price\":\"66.0000\",\"name\":\"物业管理费\",\"id\":\"\"},{\"feeItemId\":\"1334284783299108865\",\"price\":\"0.4500\",\"name\":\"抄表水费\",\"id\":\"\"}],\"resourceType\":\"house\"}', '{\"code\":200,\"msg\":\"新建成功\"}', '0', null, '2021-07-03 15:36:17');
INSERT INTO `sys_oper_log` VALUES ('279', '创建租售合约', '1', 'com.zhaoxinms.payment.controller.PaymentContractController.create()', 'POST', '1', 'admin', null, '/payment/PaymentContract', '127.0.0.1', '内网IP', '{\"blockCode\":\"01\",\"period\":36,\"resourceId\":\"1466607490526404610\",\"contractType\":\"rented\",\"userPhone\":\"13561325653\",\"userName\":\"张二\",\"rentFee\":\"150000.00\",\"beginDate\":1625297868000,\"userTrade\":\"1b82cfae-98f1-4b54-a3ae-1d1c6ad3f3f6\",\"userGender\":\"84ff10b322d74ac3a653e3176724f909\",\"userIdcard\":\"140702195603023202\",\"contractFees\":[{\"feeItemId\":\"1334284783299108865\",\"price\":\"0.4500\",\"name\":\"抄表水费\",\"id\":\"\"},{\"feeItemId\":\"6d81c6bc842e4d8ebfdfbd653b5e1f64\",\"price\":\"66.0000\",\"name\":\"物业管理费\",\"id\":\"\"},{\"feeItemId\":\"99bcebcea39444bca429a18a413aabdd\",\"price\":\"1.0000\",\"name\":\"房屋租金\",\"id\":\"\"}],\"resourceType\":\"house\"}', '{\"code\":200,\"msg\":\"新建成功\"}', '0', null, '2021-07-03 15:37:56');
INSERT INTO `sys_oper_log` VALUES ('280', '创建租售合约', '1', 'com.zhaoxinms.payment.controller.PaymentContractController.create()', 'POST', '1', 'admin', null, '/payment/PaymentContract', '127.0.0.1', '内网IP', '{\"blockCode\":\"01\",\"period\":0,\"resourceId\":\"1466607490547376129\",\"contractType\":\"selled\",\"userPhone\":\"13562356253\",\"userName\":\"张三\",\"rentFee\":\"180000.00\",\"beginDate\":1626537600000,\"userTrade\":\"0fcc751d-9ceb-4767-8755-f0189b239468\",\"userGender\":\"84ff10b322d74ac3a653e3176724f909\",\"userIdcard\":\"140702195603032603\",\"contractFees\":[{\"feeItemId\":\"6d81c6bc842e4d8ebfdfbd653b5e1f64\",\"price\":\"66.0000\",\"name\":\"物业管理费\",\"id\":\"\"},{\"feeItemId\":\"1334284783299108865\",\"price\":\"0.4500\",\"name\":\"抄表水费\",\"id\":\"\"}],\"resourceType\":\"house\"}', '{\"code\":200,\"msg\":\"新建成功\"}', '0', null, '2021-07-03 15:41:03');
INSERT INTO `sys_oper_log` VALUES ('281', '创建租售合约', '1', 'com.zhaoxinms.payment.controller.PaymentContractController.create()', 'POST', '1', 'admin', null, '/payment/PaymentContract', '127.0.0.1', '内网IP', '{\"blockCode\":\"01\",\"period\":24,\"resourceId\":\"1466607490555764737\",\"contractType\":\"rented\",\"userPhone\":\"13651325896\",\"userName\":\"张四\",\"rentFee\":\"210000.00\",\"beginDate\":1624291200000,\"userTrade\":\"27c38cf2-dc0a-4449-ab87-7eb68f7e425b\",\"userGender\":\"84ff10b322d74ac3a653e3176724f909\",\"userIdcard\":\"140236198702023256\",\"contractFees\":[{\"feeItemId\":\"1334284783299108865\",\"price\":\"0.4500\",\"name\":\"抄表水费\",\"id\":\"\"},{\"feeItemId\":\"6d81c6bc842e4d8ebfdfbd653b5e1f64\",\"price\":\"66.0000\",\"name\":\"物业管理费\",\"id\":\"\"},{\"feeItemId\":\"99bcebcea39444bca429a18a413aabdd\",\"price\":\"1.0000\",\"name\":\"房屋租金\",\"id\":\"\"}],\"resourceType\":\"house\"}', '{\"code\":200,\"msg\":\"新建成功\"}', '0', null, '2021-07-03 15:49:17');
INSERT INTO `sys_oper_log` VALUES ('282', '创建租售合约', '1', 'com.zhaoxinms.payment.controller.PaymentContractController.create()', 'POST', '1', 'admin', null, '/payment/PaymentContract', '127.0.0.1', '内网IP', '{\"blockCode\":\"01\",\"period\":0,\"resourceId\":\"1466607490597707778\",\"contractType\":\"selled\",\"userPhone\":\"13684652365\",\"userName\":\"张武\",\"rentFee\":\"240000.00\",\"beginDate\":1625298897000,\"userTrade\":\"27c38cf2-dc0a-4449-ab87-7eb68f7e425b\",\"userGender\":\"d06778318f894c4b914050601897effe\",\"userIdcard\":\"140702198702036505\",\"contractFees\":[{\"feeItemId\":\"6d81c6bc842e4d8ebfdfbd653b5e1f64\",\"price\":\"66.0000\",\"name\":\"物业管理费\",\"id\":\"\"},{\"feeItemId\":\"1334284783299108865\",\"price\":\"0.4500\",\"name\":\"抄表水费\",\"id\":\"\"}],\"resourceType\":\"house\"}', '{\"code\":200,\"msg\":\"新建成功\"}', '0', null, '2021-07-03 15:55:02');
INSERT INTO `sys_oper_log` VALUES ('283', '创建租售合约', '1', 'com.zhaoxinms.payment.controller.PaymentContractController.create()', 'POST', '1', 'admin', null, '/payment/PaymentContract', '127.0.0.1', '内网IP', '{\"blockCode\":\"01\",\"period\":0,\"resourceId\":\"1466607490606096385\",\"contractType\":\"selled\",\"userPhone\":\"13651236589\",\"userName\":\"张六\",\"rentFee\":\"270000.00\",\"beginDate\":1625299036000,\"userTrade\":\"27c38cf2-dc0a-4449-ab87-7eb68f7e425b\",\"userGender\":\"d06778318f894c4b914050601897effe\",\"userIdcard\":\"140702198802036506\",\"contractFees\":[{\"feeItemId\":\"6d81c6bc842e4d8ebfdfbd653b5e1f64\",\"price\":\"66.0000\",\"name\":\"物业管理费\",\"id\":\"\"},{\"feeItemId\":\"1334284783299108865\",\"price\":\"0.4500\",\"name\":\"抄表水费\",\"id\":\"\"}],\"resourceType\":\"house\"}', '{\"code\":200,\"msg\":\"新建成功\"}', '0', null, '2021-07-03 15:57:27');
INSERT INTO `sys_oper_log` VALUES ('284', '创建租售合约', '1', 'com.zhaoxinms.payment.controller.PaymentContractController.create()', 'POST', '1', 'admin', null, '/payment/PaymentContract', '127.0.0.1', '内网IP', '{\"blockCode\":\"01\",\"period\":24,\"resourceId\":\"1466607490622873602\",\"contractType\":\"rented\",\"userPhone\":\"13562365896\",\"userName\":\"张琪\",\"rentFee\":\"300000.00\",\"beginDate\":1626192000000,\"userTrade\":\"4d885793-2fbd-49ce-9b0e-aa709a71b2d0\",\"userGender\":\"84ff10b322d74ac3a653e3176724f909\",\"userIdcard\":\"140702196602032307\",\"contractFees\":[{\"feeItemId\":\"1334284783299108865\",\"price\":\"0.4500\",\"name\":\"抄表水费\",\"id\":\"\"},{\"feeItemId\":\"6d81c6bc842e4d8ebfdfbd653b5e1f64\",\"price\":\"66.0000\",\"name\":\"物业管理费\",\"id\":\"\"},{\"feeItemId\":\"99bcebcea39444bca429a18a413aabdd\",\"price\":\"1.0000\",\"name\":\"房屋租金\",\"id\":\"\"}],\"resourceType\":\"house\"}', '{\"code\":200,\"msg\":\"新建成功\"}', '0', null, '2021-07-03 15:59:20');
INSERT INTO `sys_oper_log` VALUES ('285', '创建租售合约', '1', 'com.zhaoxinms.payment.controller.PaymentContractController.create()', 'POST', '1', 'admin', null, '/payment/PaymentContract', '127.0.0.1', '内网IP', '{\"blockCode\":\"01\",\"period\":0,\"resourceId\":\"1466607490631262210\",\"contractType\":\"selled\",\"userPhone\":\"13256589563\",\"userName\":\"丈八\",\"rentFee\":\"330000.00\",\"beginDate\":1625500800000,\"userTrade\":\"44338471-6e77-4dd0-899d-ea0355c03277\",\"userGender\":\"d06778318f894c4b914050601897effe\",\"userIdcard\":\"140702196605062308\",\"contractFees\":[{\"feeItemId\":\"6d81c6bc842e4d8ebfdfbd653b5e1f64\",\"price\":\"66.0000\",\"name\":\"物业管理费\",\"id\":\"\"},{\"feeItemId\":\"1334284783299108865\",\"price\":\"0.4500\",\"name\":\"抄表水费\",\"id\":\"\"}],\"resourceType\":\"house\"}', '{\"code\":200,\"msg\":\"新建成功\"}', '0', null, '2021-07-03 16:01:06');
INSERT INTO `sys_oper_log` VALUES ('286', '创建租售合约', '1', 'com.zhaoxinms.payment.controller.PaymentContractController.create()', 'POST', '1', 'admin', null, '/payment/PaymentContract', '127.0.0.1', '内网IP', '{\"blockCode\":\"01\",\"period\":0,\"resourceId\":\"1466607490648039426\",\"contractType\":\"selled\",\"userPhone\":\"13251236985\",\"userName\":\"张久\",\"rentFee\":\"360000.00\",\"beginDate\":1625500800000,\"userTrade\":\"27d8cf5a-22e8-4e05-b4d6-5684a564418a\",\"userGender\":\"84ff10b322d74ac3a653e3176724f909\",\"userIdcard\":\"140702198603053209\",\"contractFees\":[{\"feeItemId\":\"6d81c6bc842e4d8ebfdfbd653b5e1f64\",\"price\":\"66.0000\",\"name\":\"物业管理费\",\"id\":\"\"},{\"feeItemId\":\"1334284783299108865\",\"price\":\"0.4500\",\"name\":\"抄表水费\",\"id\":\"\"}],\"resourceType\":\"house\"}', '{\"code\":200,\"msg\":\"新建成功\"}', '0', null, '2021-07-03 16:04:38');
INSERT INTO `sys_oper_log` VALUES ('287', '创建租售合约', '1', 'com.zhaoxinms.payment.controller.PaymentContractController.create()', 'POST', '1', 'admin', null, '/payment/PaymentContract', '127.0.0.1', '内网IP', '{\"blockCode\":\"01\",\"period\":0,\"resourceId\":\"1466607490656428033\",\"contractType\":\"selled\",\"userPhone\":\"13251325452\",\"userName\":\"张氏\",\"rentFee\":\"390000.00\",\"beginDate\":1626364800000,\"userTrade\":\"44338471-6e77-4dd0-899d-ea0355c03277\",\"userGender\":\"d06778318f894c4b914050601897effe\",\"userIdcard\":\"140702198603035610\",\"contractFees\":[{\"feeItemId\":\"6d81c6bc842e4d8ebfdfbd653b5e1f64\",\"price\":\"66.0000\",\"name\":\"物业管理费\",\"id\":\"\"},{\"feeItemId\":\"1334284783299108865\",\"price\":\"0.4500\",\"name\":\"抄表水费\",\"id\":\"\"}],\"resourceType\":\"house\"}', '{\"code\":200,\"msg\":\"新建成功\"}', '0', null, '2021-07-03 16:05:53');
INSERT INTO `sys_oper_log` VALUES ('288', '创建押金', '1', 'com.zhaoxinms.payment.controller.PaymentDepositController.create()', 'POST', '1', 'admin', null, '/payment/PaymentDeposit', '127.0.0.1', '内网IP', '{\"feeItemId\":\"1456543121520291842\",\"resourceId\":\"1466607490492850177\",\"payType\":\"9d46e7f3729843ef8dc35f9987f7f2fe\",\"operateTime\":1625300727241,\"amt\":200,\"block\":\"01\",\"resourceName\":\"01-001\",\"feeUser\":\"张一\"}', '{\"code\":200,\"msg\":\"新增押金成功\"}', '0', null, '2021-07-03 16:25:44');
INSERT INTO `sys_oper_log` VALUES ('289', '创建押金', '1', 'com.zhaoxinms.payment.controller.PaymentDepositController.create()', 'POST', '1', 'admin', null, '/payment/PaymentDeposit', '127.0.0.1', '内网IP', '{\"feeItemId\":\"1456543121520291842\",\"resourceId\":\"1466607490526404610\",\"payType\":\"13e7e17124b0404bb6411c4d11432cdb\",\"operateTime\":1625300759130,\"amt\":2000,\"block\":\"01\",\"resourceName\":\"01-002\",\"feeUser\":\"张二\"}', '{\"code\":200,\"msg\":\"新增押金成功\"}', '0', null, '2021-07-03 16:26:14');
INSERT INTO `sys_oper_log` VALUES ('290', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"excel\",\"orderNum\":\"1\",\"menuName\":\"收费项汇总表\",\"params\":{},\"parentId\":2013,\"isCache\":\"0\",\"path\":\"dailyFee\",\"component\":\"statistics/dailyFee\",\"children\":[],\"createTime\":1637660848000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2022,\"menuType\":\"C\",\"perms\":\"statistics:dailyFeeReport:dailyFeeReport\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-07-03 16:46:39');
INSERT INTO `sys_oper_log` VALUES ('291', '创建押金', '1', 'com.zhaoxinms.payment.controller.PaymentDepositController.create()', 'POST', '1', 'admin', null, '/payment/PaymentDeposit', '127.0.0.1', '内网IP', '{\"feeItemId\":\"1456543121520291842\",\"resourceId\":\"1466607490547376129\",\"payType\":\"9d46e7f3729843ef8dc35f9987f7f2fe\",\"operateTime\":1625302632600,\"amt\":2000,\"block\":\"01\",\"resourceName\":\"01-003\",\"feeUser\":\"张三\"}', '{\"code\":200,\"msg\":\"新增押金成功\"}', '0', null, '2021-07-03 16:57:45');
INSERT INTO `sys_oper_log` VALUES ('292', '创建押金', '1', 'com.zhaoxinms.payment.controller.PaymentDepositController.create()', 'POST', '1', 'admin', null, '/payment/PaymentDeposit', '127.0.0.1', '内网IP', '{\"feeItemId\":\"1456543121520291842\",\"resourceId\":\"1466607490555764737\",\"payType\":\"8bfe0cd9d0744f3e859a68c1983d3c47\",\"operateTime\":1625302667260,\"amt\":2000,\"block\":\"01\",\"resourceName\":\"01-004\",\"feeUser\":\"张四\"}', '{\"code\":200,\"msg\":\"新增押金成功\"}', '0', null, '2021-07-03 16:58:01');
INSERT INTO `sys_oper_log` VALUES ('293', '创建押金', '1', 'com.zhaoxinms.payment.controller.PaymentDepositController.create()', 'POST', '1', 'admin', null, '/payment/PaymentDeposit', '127.0.0.1', '内网IP', '{\"feeItemId\":\"1456543121520291842\",\"resourceId\":\"1466607490597707778\",\"payType\":\"9d46e7f3729843ef8dc35f9987f7f2fe\",\"operateTime\":1625302683928,\"amt\":2000,\"block\":\"01\",\"resourceName\":\"01-005\",\"feeUser\":\"张武\"}', '{\"code\":200,\"msg\":\"新增押金成功\"}', '0', null, '2021-07-03 16:58:19');
INSERT INTO `sys_oper_log` VALUES ('294', '创建押金', '1', 'com.zhaoxinms.payment.controller.PaymentDepositController.create()', 'POST', '1', 'admin', null, '/payment/PaymentDeposit', '127.0.0.1', '内网IP', '{\"feeItemId\":\"1456543121520291842\",\"resourceId\":\"1466607490606096385\",\"payType\":\"13e7e17124b0404bb6411c4d11432cdb\",\"operateTime\":1625302703466,\"amt\":2000,\"block\":\"01\",\"resourceName\":\"01-006\",\"feeUser\":\"张六\"}', '{\"code\":200,\"msg\":\"新增押金成功\"}', '0', null, '2021-07-03 16:58:34');
INSERT INTO `sys_oper_log` VALUES ('295', '创建押金', '1', 'com.zhaoxinms.payment.controller.PaymentDepositController.create()', 'POST', '1', 'admin', null, '/payment/PaymentDeposit', '127.0.0.1', '内网IP', '{\"feeItemId\":\"1456543121520291842\",\"resourceId\":\"1466607490622873602\",\"payType\":\"9d46e7f3729843ef8dc35f9987f7f2fe\",\"operateTime\":1625302717072,\"amt\":2000,\"block\":\"01\",\"resourceName\":\"01-007\",\"feeUser\":\"张琪\"}', '{\"code\":200,\"msg\":\"新增押金成功\"}', '0', null, '2021-07-03 16:58:52');
INSERT INTO `sys_oper_log` VALUES ('296', '创建押金', '1', 'com.zhaoxinms.payment.controller.PaymentDepositController.create()', 'POST', '1', 'admin', null, '/payment/PaymentDeposit', '127.0.0.1', '内网IP', '{\"feeItemId\":\"1456543121520291842\",\"resourceId\":\"1466607490631262210\",\"payType\":\"9d46e7f3729843ef8dc35f9987f7f2fe\",\"operateTime\":1625302783049,\"amt\":2000,\"block\":\"01\",\"resourceName\":\"01-008\",\"feeUser\":\"丈八\"}', '{\"code\":200,\"msg\":\"新增押金成功\"}', '0', null, '2021-07-03 16:59:54');
INSERT INTO `sys_oper_log` VALUES ('297', '创建押金', '1', 'com.zhaoxinms.payment.controller.PaymentDepositController.create()', 'POST', '1', 'admin', null, '/payment/PaymentDeposit', '127.0.0.1', '内网IP', '{\"feeItemId\":\"1456543121520291842\",\"resourceId\":\"1466607490648039426\",\"payType\":\"9d46e7f3729843ef8dc35f9987f7f2fe\",\"operateTime\":1625302802698,\"amt\":2000,\"block\":\"01\",\"resourceName\":\"01-009\",\"feeUser\":\"张久\"}', '{\"code\":200,\"msg\":\"新增押金成功\"}', '0', null, '2021-07-03 17:01:10');
INSERT INTO `sys_oper_log` VALUES ('298', '创建押金', '1', 'com.zhaoxinms.payment.controller.PaymentDepositController.create()', 'POST', '1', 'admin', null, '/payment/PaymentDeposit', '127.0.0.1', '内网IP', '{\"feeItemId\":\"1456543121520291842\",\"resourceId\":\"1466607490656428033\",\"payType\":\"9d46e7f3729843ef8dc35f9987f7f2fe\",\"operateTime\":1625302871945,\"amt\":2000,\"block\":\"01\",\"resourceName\":\"01-010\",\"feeUser\":\"张氏\"}', '{\"code\":200,\"msg\":\"新增押金成功\"}', '0', null, '2021-07-03 17:01:25');
INSERT INTO `sys_oper_log` VALUES ('299', '临时收费', '1', 'com.zhaoxinms.payment.controller.PaymentTempController.create()', 'POST', '1', 'admin', null, '/payment/PaymentTemp', '127.0.0.1', '内网IP', '{\"feeItemId\":\"1456546272025604098\",\"payType\":\"13e7e17124b0404bb6411c4d11432cdb\",\"operateTime\":1625302915064,\"amt\":20000,\"block\":\"\",\"feeUser\":\"王老五\"}', '{\"code\":200,\"msg\":\"新增费用成功\"}', '0', null, '2021-07-03 17:03:19');
INSERT INTO `sys_oper_log` VALUES ('300', '新增预付款', '1', 'com.zhaoxinms.payment.controller.PaymentPreController.deposit()', 'POST', '1', 'admin', null, '/payment/PaymentPre/add', '127.0.0.1', '内网IP', '{\"feeItemId\":\"6d81c6bc842e4d8ebfdfbd653b5e1f64\",\"resourceId\":\"1466607490492850177\",\"payType\":\"9d46e7f3729843ef8dc35f9987f7f2fe\",\"operateTime\":1625303479501,\"useFeeItem\":\"1\",\"amt\":10000,\"block\":\"01\",\"resourceName\":\"01-001\",\"feeUser\":\"张一\"}', '{\"code\":200,\"msg\":\"预存成功\"}', '0', null, '2021-07-03 17:11:39');
INSERT INTO `sys_oper_log` VALUES ('301', '收费数据生成', '1', 'com.zhaoxinms.payment.controller.PaymentBillCreateController.generate()', 'POST', '1', 'admin', null, '/payment/PaymentBillCreate/generate', '127.0.0.1', '内网IP', '{\"feeItemId\":\"6d81c6bc842e4d8ebfdfbd653b5e1f64\",\"beginDate\":1625241600000,\"endDate\":1628092800000,\"price\":\"66.0000\",\"deadline\":1629043200000}', '{\"code\":200,\"msg\":\"生成数据成功\"}', '0', null, '2021-07-17 17:18:13');
INSERT INTO `sys_oper_log` VALUES ('302', '更新租售合约', '2', 'com.zhaoxinms.payment.controller.PaymentContractController.update()', 'PUT', '1', 'admin', null, '/payment/PaymentContract/1411227278918098946', '127.0.0.1', '内网IP', '1411227278918098946 {\"blockCode\":\"01\",\"period\":0,\"resourceId\":\"1466607490492850177\",\"contractType\":\"selled\",\"userPhone\":\"13265412321\",\"userName\":\"张一\",\"rentFee\":\"120000.00\",\"beginDate\":1625241600000,\"userTrade\":\"0fcc751d-9ceb-4767-8755-f0189b239468\",\"userGender\":\"84ff10b322d74ac3a653e3176724f909\",\"userIdcard\":\"140702198502036501\",\"contractFees\":[{\"feeItemId\":\"6d81c6bc842e4d8ebfdfbd653b5e1f64\",\"beginDate\":1625241600000,\"price\":\"66.0000\",\"contractId\":\"1411227278918098946\",\"name\":\"物业管理费\",\"id\":\"1411227278964236289\"},{\"feeItemId\":\"1334284783299108865\",\"beginDate\":1625241600000,\"price\":\"0.4500\",\"contractId\":\"1411227278918098946\",\"name\":\"抄表水费\",\"id\":\"1411227278993596418\"}],\"resourceType\":\"house\"}', '{\"code\":200,\"msg\":\"更新成功\"}', '0', null, '2021-07-17 17:22:23');
INSERT INTO `sys_oper_log` VALUES ('303', '抄表数据导入', '6', 'com.zhaoxinms.payment.controller.PaymentMeterImportController.importPreview()', 'GET', '1', 'admin', null, '/payment/PaymentMeterImport/Import', '127.0.0.1', '内网IP', '{}', '{\"code\":200,\"data\":{\"num\":\"10\"},\"msg\":\"Success\"}', '0', null, '2021-07-17 17:36:25');
INSERT INTO `sys_oper_log` VALUES ('304', '收费数据生成', '1', 'com.zhaoxinms.payment.controller.PaymentBillCreateController.generate()', 'POST', '1', 'admin', null, '/payment/PaymentBillCreate/generate', '127.0.0.1', '内网IP', '{\"feeItemId\":\"6d81c6bc842e4d8ebfdfbd653b5e1f64\",\"beginDate\":1623772800000,\"endDate\":1626796800000,\"price\":\"66.0000\",\"deadline\":1629129600000}', '{\"code\":200,\"msg\":\"生成数据成功\"}', '0', null, '2021-07-17 17:38:59');
INSERT INTO `sys_oper_log` VALUES ('305', '更新抄表数据', '2', 'com.zhaoxinms.payment.controller.PaymentMeterController.update()', 'PUT', '1', 'admin', null, '/payment/PaymentMeter/1416330940963168259', '127.0.0.1', '内网IP', '1416330940963168259 {\"feeItemId\":\"1334284783299108865\",\"resourceId\":\"1466607490555764737\",\"multiple\":\"1.10\",\"resourceName\":\"01-004\",\"currentIndex\":\"1.00\",\"result\":\"1.10\",\"loss\":\"0.00\",\"feeItemName\":\"抄表水费\",\"id\":\"1416330940963168259\",\"lastIndex\":\"0.00\",\"currentIndexDate\":1626278400000,\"resourceType\":\"house\"}', '{\"code\":200,\"msg\":\"更新成功\"}', '0', null, '2021-07-17 17:49:11');
INSERT INTO `sys_oper_log` VALUES ('306', '收费数据生成', '1', 'com.zhaoxinms.payment.controller.PaymentBillCreateController.generate()', 'POST', '1', 'admin', null, '/payment/PaymentBillCreate/generate', '127.0.0.1', '内网IP', '{\"feeItemId\":\"99bcebcea39444bca429a18a413aabdd\",\"beginDate\":1623168000000,\"endDate\":1627574400000,\"price\":\"1.0000\",\"deadline\":1628697600000}', '{\"code\":200,\"msg\":\"生成数据成功\"}', '0', null, '2021-07-17 17:51:32');
INSERT INTO `sys_oper_log` VALUES ('307', '收费数据支付', '2', 'com.zhaoxinms.payment.controller.PaymentBillPayController.pay()', 'POST', '1', 'admin', null, '/payment/PaymentBillPay/payBill', '127.0.0.1', '内网IP', '{\"canUsePrePayMoney\":\"10000.00\",\"paymentBills\":[{\"feeItemId\":\"6d81c6bc842e4d8ebfdfbd653b5e1f64\",\"payLogNo\":\"\",\"resourceId\":\"1466607490492850177\",\"endDate\":1656691200000,\"num\":\"200.00\",\"multiple\":1,\"discount\":\"0.00\",\"payLogId\":\"\",\"resourceName\":\"01-001\",\"receivable\":\"13200.00\",\"currentIndex\":0,\"beginDate\":1625241600000,\"loss\":0,\"total\":\"13200.00\",\"price\":\"66.0000\",\"lateFee\":\"0\",\"feeItemName\":\"物业管理费\",\"id\":\"1416326361362014209\",\"lastIndex\":0,\"deadline\":1629043200000,\"feeUser\":\"张一\"},{\"feeItemId\":\"1334284783299108865\",\"payLogNo\":\"\",\"resourceId\":\"1466607490492850177\",\"endDate\":1627747200000,\"num\":\"100.00\",\"multiple\":1,\"discount\":\"0.00\",\"payLogId\":\"\",\"resourceName\":\"01-001\",\"receivable\":\"45.00\",\"currentIndex\":100,\"beginDate\":1625241600000,\"loss\":0,\"total\":\"45.00\",\"price\":\"0.4500\",\"lateFee\":\"0\",\"feeItemName\":\"抄表水费\",\"id\":\"1416331156458119169\",\"lastIndex\":0,\"deadline\":1630339200000,\"feeUser\":\"张一\"}],\"accountForms\":[{\"feeItemId\":\"6d81c6bc842e4d8ebfdfbd653b5e1f64\",\"resourceId\":\"1466607490492850177\",\"payMoney\":\"10000.00\",\"amt\":\"10000.00\",\"block\":\"01\",\"id\":\"1411251277693788161\"}],\"discountMoney\":\"0.00\",\"receivableMoney\":\"13245.00\",\"usePreSave\":true,\"usePrePay\":true,\"changeMoney\":\"0\",\"lateFeeMoney\":\"0.00\",\"payMoney\":\"4000.00\",\"prePayMoney\":\"10000.00\",\"preSaveMoney\":\"755.00\",\"payMethod\":\"13e7e17124b0404bb6411c4d11432cdb\",\"itemTotalMoney\":\"13245\"}', '{\"code\":200,\"data\":{\"certificate\":\"\",\"changeMoney\":\"0\",\"comment\":\"\",\"creatorTime\":1626523474347,\"creatorUserId\":\"1\",\"discountMoney\":\"0.00\",\"feeType\":\"house\",\"id\":\"1416368224999645185\",\"itemTotalMoney\":\"13245\",\"lateFeeMoney\":\"0.00\",\"name\":\"物业管理费,抄表水费\",\"payMethod\":\"13e7e17124b0404bb6411c4d11432cdb\",\"payMoney\":\"4000.00\",\"payNo\":\"PAY2021071700201\",\"payTime\":1626523474324,\"payerIdcard\":\"140702198502036501\",\"payerName\":\"张一\",\"payerPhone\":\"13265412321\",\"prePayMoney\":\"10000.00\",\"preSaveMoney\":\"755.00\",\"receivableMoney\":\"13245.00\",\"resourceName\":\"01-001\",\"type\":\"pay\"},\"msg\":\"Success\"}', '0', null, '2021-07-17 20:04:34');
INSERT INTO `sys_oper_log` VALUES ('308', '临时收费', '1', 'com.zhaoxinms.payment.controller.PaymentTempController.create()', 'POST', '1', 'admin', null, '/payment/PaymentTemp', '127.0.0.1', '内网IP', '{\"feeItemId\":\"1456545461837066241\",\"resourceId\":\"1466607490492850177\",\"payType\":\"9d46e7f3729843ef8dc35f9987f7f2fe\",\"operateTime\":1626523661548,\"amt\":500,\"block\":\"01\",\"resourceName\":\"01-001\",\"feeUser\":\"张一\"}', '{\"code\":200,\"msg\":\"新增费用成功\"}', '0', null, '2021-07-17 20:07:54');
INSERT INTO `sys_oper_log` VALUES ('309', '临时收费退款', '2', 'com.zhaoxinms.payment.controller.PaymentTempController.update()', 'PUT', '1', 'admin', null, '/payment/PaymentTemp/1416369066163118081', '127.0.0.1', '内网IP', '1416369066163118081 {\"refundType\":\"13e7e17124b0404bb6411c4d11432cdb\",\"refundTime\":1626523965978,\"remark\":\"321\"}', '{\"code\":200,\"msg\":\"退还费用成功\"}', '0', null, '2021-07-17 20:12:52');
INSERT INTO `sys_oper_log` VALUES ('310', '创建押金', '1', 'com.zhaoxinms.payment.controller.PaymentDepositController.create()', 'POST', '1', 'admin', null, '/payment/PaymentDeposit', '127.0.0.1', '内网IP', '{\"feeItemId\":\"c20e0daa4d454ae6977099d6559a813a\",\"resourceId\":\"1466607490492850177\",\"payType\":\"9d46e7f3729843ef8dc35f9987f7f2fe\",\"operateTime\":1626523997082,\"amt\":400,\"block\":\"01\",\"resourceName\":\"01-001\",\"feeUser\":\"张一\"}', '{\"code\":200,\"msg\":\"新增押金成功\"}', '0', null, '2021-07-17 20:13:24');
INSERT INTO `sys_oper_log` VALUES ('311', '押金退款', '2', 'com.zhaoxinms.payment.controller.PaymentDepositController.update()', 'PUT', '1', 'admin', null, '/payment/PaymentDeposit/1416370449671069697', '127.0.0.1', '内网IP', '1416370449671069697 {\"refundType\":\"13e7e17124b0404bb6411c4d11432cdb\",\"refundTime\":1626524009658}', '{\"code\":200,\"msg\":\"退还押金成功\"}', '0', null, '2021-07-17 20:13:33');
INSERT INTO `sys_oper_log` VALUES ('312', '抄表数据导入', '6', 'com.zhaoxinms.payment.controller.PaymentMeterImportController.importPreview()', 'GET', '1', 'admin', null, '/payment/PaymentMeterImport/Import', '127.0.0.1', '内网IP', '{}', null, '1', '校验第1条数据失败，本期度数不能小于上期度数', '2021-08-18 07:03:16');
INSERT INTO `sys_oper_log` VALUES ('313', '抄表数据导入', '6', 'com.zhaoxinms.payment.controller.PaymentMeterImportController.importPreview()', 'GET', '1', 'admin', null, '/payment/PaymentMeterImport/Import', '127.0.0.1', '内网IP', '{}', null, '1', 'com.zhaoxinms.payment.mapper.PaymentMeterMapper.insert (batch index #1) failed. Cause: java.sql.BatchUpdateException: Field \'current_index_date\' doesn\'t have a default value\n; Field \'current_index_date\' doesn\'t have a default value; nested exception is java.sql.BatchUpdateException: Field \'current_index_date\' doesn\'t have a default value', '2021-08-18 07:04:10');
INSERT INTO `sys_oper_log` VALUES ('314', '抄表数据导入', '6', 'com.zhaoxinms.payment.controller.PaymentMeterImportController.importPreview()', 'GET', '1', 'admin', null, '/payment/PaymentMeterImport/Import', '127.0.0.1', '内网IP', '{}', null, '1', 'com.zhaoxinms.payment.mapper.PaymentMeterMapper.insert (batch index #1) failed. Cause: java.sql.BatchUpdateException: Field \'current_index_date\' doesn\'t have a default value\n; Field \'current_index_date\' doesn\'t have a default value; nested exception is java.sql.BatchUpdateException: Field \'current_index_date\' doesn\'t have a default value', '2021-08-18 07:05:17');
INSERT INTO `sys_oper_log` VALUES ('315', '抄表数据导入', '6', 'com.zhaoxinms.payment.controller.PaymentMeterImportController.importPreview()', 'GET', '1', 'admin', null, '/payment/PaymentMeterImport/Import', '127.0.0.1', '内网IP', '{}', null, '1', '校验第1条数据失败，日期格式不正确', '2021-08-18 07:14:11');
INSERT INTO `sys_oper_log` VALUES ('316', '抄表数据导入', '6', 'com.zhaoxinms.payment.controller.PaymentMeterImportController.importPreview()', 'GET', '1', 'admin', null, '/payment/PaymentMeterImport/Import', '127.0.0.1', '内网IP', '{}', null, '1', '校验第1条数据失败，本次抄表时间不能为空', '2021-08-18 07:16:07');
INSERT INTO `sys_oper_log` VALUES ('317', '抄表数据导入', '6', 'com.zhaoxinms.payment.controller.PaymentMeterImportController.importPreview()', 'GET', '1', 'admin', null, '/payment/PaymentMeterImport/Import', '127.0.0.1', '内网IP', '{}', null, '1', 'com.zhaoxinms.payment.mapper.PaymentMeterMapper.insert (batch index #2) failed. 1 prior sub executor(s) completed successfully, but will be rolled back. Cause: java.sql.BatchUpdateException: Field \'current_index_date\' doesn\'t have a default value\n; Field \'current_index_date\' doesn\'t have a default value; nested exception is java.sql.BatchUpdateException: Field \'current_index_date\' doesn\'t have a default value', '2021-08-18 07:17:07');
INSERT INTO `sys_oper_log` VALUES ('318', '抄表数据导入', '6', 'com.zhaoxinms.payment.controller.PaymentMeterImportController.importPreview()', 'GET', '1', 'admin', null, '/payment/PaymentMeterImport/Import', '127.0.0.1', '内网IP', '{}', null, '1', 'com.zhaoxinms.payment.mapper.PaymentMeterMapper.insert (batch index #2) failed. 1 prior sub executor(s) completed successfully, but will be rolled back. Cause: java.sql.BatchUpdateException: Field \'current_index_date\' doesn\'t have a default value\n; Field \'current_index_date\' doesn\'t have a default value; nested exception is java.sql.BatchUpdateException: Field \'current_index_date\' doesn\'t have a default value', '2021-08-18 07:17:22');
INSERT INTO `sys_oper_log` VALUES ('319', '抄表数据导入', '6', 'com.zhaoxinms.payment.controller.PaymentMeterImportController.importPreview()', 'GET', '1', 'admin', null, '/payment/PaymentMeterImport/Import', '127.0.0.1', '内网IP', '{}', null, '1', '校验第1条数据失败，上次抄表时间格式不正确', '2021-08-18 07:23:58');
INSERT INTO `sys_oper_log` VALUES ('320', '抄表数据导入', '6', 'com.zhaoxinms.payment.controller.PaymentMeterImportController.importPreview()', 'GET', '1', 'admin', null, '/payment/PaymentMeterImport/Import', '127.0.0.1', '内网IP', '{}', null, '1', '校验第1条数据失败，上次抄表时间格式不正确', '2021-08-18 07:24:08');
INSERT INTO `sys_oper_log` VALUES ('321', '抄表数据导入', '6', 'com.zhaoxinms.payment.controller.PaymentMeterImportController.importPreview()', 'GET', '1', 'admin', null, '/payment/PaymentMeterImport/Import', '127.0.0.1', '内网IP', '{}', null, '1', '校验第2条数据失败，本次读表时间格式不正确', '2021-08-18 07:31:14');
INSERT INTO `sys_oper_log` VALUES ('322', '抄表数据导入', '6', 'com.zhaoxinms.payment.controller.PaymentMeterImportController.importPreview()', 'GET', '1', 'admin', null, '/payment/PaymentMeterImport/Import', '127.0.0.1', '内网IP', '{}', null, '1', '校验第2条数据失败，本次读表时间格式不正确', '2021-08-18 07:31:42');
INSERT INTO `sys_oper_log` VALUES ('323', '抄表数据导入', '6', 'com.zhaoxinms.payment.controller.PaymentMeterImportController.importPreview()', 'GET', '1', 'admin', null, '/payment/PaymentMeterImport/Import', '127.0.0.1', '内网IP', '{}', null, '1', '校验第2条数据失败，上次读表时间格式不正确', '2021-08-18 07:32:27');
INSERT INTO `sys_oper_log` VALUES ('324', '抄表数据导入', '6', 'com.zhaoxinms.payment.controller.PaymentMeterImportController.importPreview()', 'GET', '1', 'admin', null, '/payment/PaymentMeterImport/Import', '127.0.0.1', '内网IP', '{}', null, '1', '校验第2条数据失败，上次读表时间格式不正确', '2021-08-18 07:32:33');
INSERT INTO `sys_oper_log` VALUES ('325', '抄表数据导入', '6', 'com.zhaoxinms.payment.controller.PaymentMeterImportController.importPreview()', 'GET', '1', 'admin', null, '/payment/PaymentMeterImport/Import', '127.0.0.1', '内网IP', '{}', null, '1', '校验第2条数据失败，上次读表时间格式不正确', '2021-08-18 07:35:19');
INSERT INTO `sys_oper_log` VALUES ('326', '抄表数据导入', '6', 'com.zhaoxinms.payment.controller.PaymentMeterImportController.importPreview()', 'GET', '1', 'admin', null, '/payment/PaymentMeterImport/Import', '127.0.0.1', '内网IP', '{}', null, '1', '校验第2条数据失败，上次读表时间格式不正确', '2021-08-18 07:35:29');
INSERT INTO `sys_oper_log` VALUES ('327', '抄表数据导入', '6', 'com.zhaoxinms.payment.controller.PaymentMeterImportController.importPreview()', 'GET', '1', 'admin', null, '/payment/PaymentMeterImport/Import', '127.0.0.1', '内网IP', '{}', null, '1', '校验第2条数据失败，上次读表时间格式不正确', '2021-08-18 07:35:35');
INSERT INTO `sys_oper_log` VALUES ('328', '抄表数据导入', '6', 'com.zhaoxinms.payment.controller.PaymentMeterImportController.importPreview()', 'GET', '1', 'admin', null, '/payment/PaymentMeterImport/Import', '127.0.0.1', '内网IP', '{}', null, '1', '校验第2条数据失败，上次读表时间格式不正确', '2021-08-18 07:36:13');
INSERT INTO `sys_oper_log` VALUES ('329', '抄表数据导入', '6', 'com.zhaoxinms.payment.controller.PaymentMeterImportController.importPreview()', 'GET', '1', 'admin', null, '/payment/PaymentMeterImport/Import', '127.0.0.1', '内网IP', '{}', null, '1', '校验第2条数据失败，上次读表时间格式不正确', '2021-08-18 07:37:24');
INSERT INTO `sys_oper_log` VALUES ('330', '抄表数据导入', '6', 'com.zhaoxinms.payment.controller.PaymentMeterImportController.importPreview()', 'GET', '1', 'admin', null, '/payment/PaymentMeterImport/Import', '127.0.0.1', '内网IP', '{}', null, '1', 'com.zhaoxinms.payment.mapper.PaymentMeterMapper.insert (batch index #2) failed. 1 prior sub executor(s) completed successfully, but will be rolled back. Cause: java.sql.BatchUpdateException: Incorrect decimal value: \'160d\' for column \'current_index\' at row 1\n; uncategorized SQLException; SQL state [HY000]; error code [1366]; Incorrect decimal value: \'160d\' for column \'current_index\' at row 1; nested exception is java.sql.BatchUpdateException: Incorrect decimal value: \'160d\' for column \'current_index\' at row 1', '2021-08-18 07:40:04');
INSERT INTO `sys_oper_log` VALUES ('331', '抄表数据导入', '6', 'com.zhaoxinms.payment.controller.PaymentMeterImportController.importPreview()', 'GET', '1', 'admin', null, '/payment/PaymentMeterImport/Import', '127.0.0.1', '内网IP', '{}', null, '1', 'com.zhaoxinms.payment.mapper.PaymentMeterMapper.insert (batch index #2) failed. 1 prior sub executor(s) completed successfully, but will be rolled back. Cause: java.sql.BatchUpdateException: Incorrect decimal value: \'160d\' for column \'current_index\' at row 1\n; uncategorized SQLException; SQL state [HY000]; error code [1366]; Incorrect decimal value: \'160d\' for column \'current_index\' at row 1; nested exception is java.sql.BatchUpdateException: Incorrect decimal value: \'160d\' for column \'current_index\' at row 1', '2021-08-18 07:42:17');
INSERT INTO `sys_oper_log` VALUES ('332', '抄表数据导入', '6', 'com.zhaoxinms.payment.controller.PaymentMeterImportController.importPreview()', 'GET', '1', 'admin', null, '/payment/PaymentMeterImport/Import', '127.0.0.1', '内网IP', '{}', null, '1', 'com.zhaoxinms.payment.mapper.PaymentMeterMapper.insert (batch index #2) failed. 1 prior sub executor(s) completed successfully, but will be rolled back. Cause: java.sql.BatchUpdateException: Incorrect decimal value: \'160d\' for column \'current_index\' at row 1\n; uncategorized SQLException; SQL state [HY000]; error code [1366]; Incorrect decimal value: \'160d\' for column \'current_index\' at row 1; nested exception is java.sql.BatchUpdateException: Incorrect decimal value: \'160d\' for column \'current_index\' at row 1', '2021-08-18 07:45:34');
INSERT INTO `sys_oper_log` VALUES ('333', '抄表数据导入', '6', 'com.zhaoxinms.payment.controller.PaymentMeterImportController.importPreview()', 'GET', '1', 'admin', null, '/payment/PaymentMeterImport/Import', '127.0.0.1', '内网IP', '{}', null, '1', 'For input string: \"160s\"', '2021-08-18 07:48:53');
INSERT INTO `sys_oper_log` VALUES ('334', '抄表数据导入', '6', 'com.zhaoxinms.payment.controller.PaymentMeterImportController.importPreview()', 'GET', '1', 'admin', null, '/payment/PaymentMeterImport/Import', '127.0.0.1', '内网IP', '{}', null, '1', 'For input string: \"160s\"', '2021-08-18 07:49:10');
INSERT INTO `sys_oper_log` VALUES ('335', '抄表数据导入', '6', 'com.zhaoxinms.payment.controller.PaymentMeterImportController.importPreview()', 'GET', '1', 'admin', null, '/payment/PaymentMeterImport/Import', '127.0.0.1', '内网IP', '{}', null, '1', '校验第2条数据失败，本期度数格式不正确', '2021-08-18 07:50:53');
INSERT INTO `sys_oper_log` VALUES ('336', '抄表数据导入', '6', 'com.zhaoxinms.payment.controller.PaymentMeterImportController.importPreview()', 'GET', '1', 'admin', null, '/payment/PaymentMeterImport/Import', '127.0.0.1', '内网IP', '{}', null, '1', '校验第2条数据失败，本期度数格式不正确', '2021-08-18 07:51:22');
INSERT INTO `sys_oper_log` VALUES ('337', '抄表数据导入', '6', 'com.zhaoxinms.payment.controller.PaymentMeterImportController.importPreview()', 'GET', '1', 'admin', null, '/payment/PaymentMeterImport/Import', '127.0.0.1', '内网IP', '{}', null, '1', 'For input string: \"110e\"', '2021-08-18 07:51:37');
INSERT INTO `sys_oper_log` VALUES ('338', '抄表数据导入', '6', 'com.zhaoxinms.payment.controller.PaymentMeterImportController.importPreview()', 'GET', '1', 'admin', null, '/payment/PaymentMeterImport/Import', '127.0.0.1', '内网IP', '{}', null, '1', '校验第2条数据失败，本期度数格式不正确', '2021-08-18 09:33:56');
INSERT INTO `sys_oper_log` VALUES ('339', '抄表数据导入', '6', 'com.zhaoxinms.payment.controller.PaymentMeterImportController.importPreview()', 'GET', '1', 'admin', null, '/payment/PaymentMeterImport/Import', '127.0.0.1', '内网IP', '{}', null, '1', '校验第2条数据失败，上期度数格式不正确', '2021-08-18 09:34:27');
INSERT INTO `sys_oper_log` VALUES ('340', '抄表数据导入', '6', 'com.zhaoxinms.payment.controller.PaymentMeterImportController.importPreview()', 'GET', '1', 'admin', null, '/payment/PaymentMeterImport/Import', '127.0.0.1', '内网IP', '{}', null, '1', '校验第2条数据失败，上期度数格式不正确', '2021-08-18 09:35:45');
INSERT INTO `sys_oper_log` VALUES ('341', '抄表数据导入', '6', 'com.zhaoxinms.payment.controller.PaymentMeterImportController.importPreview()', 'GET', '1', 'admin', null, '/payment/PaymentMeterImport/Import', '127.0.0.1', '内网IP', '{}', null, '1', '校验第2条数据失败，上期度数格式不正确', '2021-08-18 09:35:48');
INSERT INTO `sys_oper_log` VALUES ('342', '抄表数据导入', '6', 'com.zhaoxinms.payment.controller.PaymentMeterImportController.importPreview()', 'GET', '1', 'admin', null, '/payment/PaymentMeterImport/Import', '127.0.0.1', '内网IP', '{}', null, '1', '', '2021-08-18 09:36:23');
INSERT INTO `sys_oper_log` VALUES ('343', '抄表数据导入', '6', 'com.zhaoxinms.payment.controller.PaymentMeterImportController.importPreview()', 'GET', '1', 'admin', null, '/payment/PaymentMeterImport/Import', '127.0.0.1', '内网IP', '{}', '{\"code\":200,\"data\":{\"num\":\"7\"},\"msg\":\"Success\"}', '0', null, '2021-08-18 09:37:23');
INSERT INTO `sys_oper_log` VALUES ('344', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"server\",\"orderNum\":\"9\",\"menuName\":\"欠费数据\",\"params\":{},\"parentId\":2013,\"isCache\":\"0\",\"path\":\"overdue\",\"component\":\"statistics/overdue\",\"children\":[],\"createTime\":1637660682000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2021,\"menuType\":\"C\",\"perms\":\"statistics:paymentBill:overdue\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-08-18 09:46:47');
INSERT INTO `sys_oper_log` VALUES ('345', '收费数据支付', '2', 'com.zhaoxinms.payment.controller.PaymentBillPayController.pay()', 'POST', '1', 'admin', null, '/payment/PaymentBillPay/payBill', '127.0.0.1', '内网IP', '{\"canUsePrePayMoney\":\"9.00\",\"paymentBills\":[{\"feeItemId\":\"1334284783299108865\",\"payLogNo\":\"\",\"resourceId\":\"1466607490492850177\",\"endDate\":1630339200000,\"num\":\"20.00\",\"multiple\":1,\"discount\":\"0.00\",\"payLogId\":\"\",\"resourceName\":\"01-001\",\"receivable\":\"9.00\",\"currentIndex\":120,\"beginDate\":1627833600000,\"loss\":0,\"total\":\"9.00\",\"price\":\"0.4500\",\"lateFee\":\"0\",\"feeItemName\":\"抄表水费\",\"id\":\"1427808297376817154\",\"lastIndex\":100,\"deadline\":1632240000000,\"feeUser\":\"张一\"}],\"accountForms\":[{\"feeItemId\":\"6d81c6bc842e4d8ebfdfbd653b5e1f64\",\"resourceId\":\"1466607490492850177\",\"payMoney\":\"0.00\",\"amt\":\"0.00\",\"block\":\"01\",\"id\":\"1411251277693788161\"},{\"feeItemId\":\"\",\"resourceId\":\"1466607490492850177\",\"payMoney\":\"9\",\"amt\":\"755.00\",\"block\":\"01\",\"id\":\"1416368225268080641\"}],\"discountMoney\":\"0.00\",\"receivableMoney\":\"9.00\",\"usePreSave\":false,\"usePrePay\":true,\"changeMoney\":\"0.00\",\"lateFeeMoney\":\"0.00\",\"payMoney\":\"0.00\",\"prePayMoney\":\"9.00\",\"preSaveMoney\":\"0\",\"payMethod\":\"8bfe0cd9d0744f3e859a68c1983d3c47\",\"itemTotalMoney\":\"9\"}', '{\"code\":200,\"data\":{\"certificate\":\"\",\"changeMoney\":\"0.00\",\"comment\":\"\",\"creatorTime\":1629254659181,\"creatorUserId\":\"1\",\"discountMoney\":\"0.00\",\"feeType\":\"house\",\"id\":\"1427823644477759490\",\"itemTotalMoney\":\"9\",\"lateFeeMoney\":\"0.00\",\"name\":\"抄表水费\",\"payMethod\":\"8bfe0cd9d0744f3e859a68c1983d3c47\",\"payMoney\":\"0.00\",\"payNo\":\"PAY2021081800201\",\"payTime\":1629254659122,\"payerIdcard\":\"140702198502036501\",\"payerName\":\"张一\",\"payerPhone\":\"13265412321\",\"prePayMoney\":\"9.00\",\"preSaveMoney\":\"0\",\"receivableMoney\":\"9.00\",\"resourceName\":\"01-001\",\"type\":\"pay\"},\"msg\":\"Success\"}', '0', null, '2021-08-18 10:44:19');
INSERT INTO `sys_oper_log` VALUES ('346', '收费数据支付', '2', 'com.zhaoxinms.payment.controller.PaymentBillPayController.pay()', 'POST', '1', 'admin', null, '/payment/PaymentBillPay/payBill', '127.0.0.1', '内网IP', '{\"canUsePrePayMoney\":\"0\",\"paymentBills\":[{\"feeItemId\":\"6d81c6bc842e4d8ebfdfbd653b5e1f64\",\"payLogNo\":\"\",\"resourceId\":\"1466607490547376129\",\"endDate\":1657987200000,\"num\":\"260.00\",\"multiple\":1,\"discount\":\"0.00\",\"payLogId\":\"\",\"resourceName\":\"01-003\",\"receivable\":\"17160.00\",\"currentIndex\":0,\"beginDate\":1626537600000,\"loss\":0,\"total\":\"17160.00\",\"price\":\"66.0000\",\"lateFee\":\"0\",\"feeItemName\":\"物业管理费\",\"id\":\"1416326361408151554\",\"lastIndex\":0,\"deadline\":1629043200000,\"feeUser\":\"张三\"},{\"feeItemId\":\"1334284783299108865\",\"payLogNo\":\"\",\"resourceId\":\"1466607490547376129\",\"endDate\":1627747200000,\"num\":\"30.00\",\"multiple\":1,\"discount\":\"0.00\",\"payLogId\":\"\",\"resourceName\":\"01-003\",\"receivable\":\"13.50\",\"currentIndex\":30,\"beginDate\":1626537600000,\"loss\":0,\"total\":\"13.50\",\"price\":\"0.4500\",\"lateFee\":\"0\",\"feeItemName\":\"抄表水费\",\"id\":\"1416331156466507779\",\"lastIndex\":0,\"deadline\":1630339200000,\"feeUser\":\"张三\"},{\"feeItemId\":\"1334284783299108865\",\"payLogNo\":\"\",\"resourceId\":\"1466607490547376129\",\"endDate\":1630339200000,\"num\":\"1.00\",\"multiple\":1,\"discount\":\"0.00\",\"payLogId\":\"\",\"resourceName\":\"01-003\",\"receivable\":\"0.45\",\"currentIndex\":31,\"beginDate\":1627833600000,\"loss\":0,\"total\":\"0.45\",\"price\":\"0.4500\",\"lateFee\":\"0\",\"feeItemName\":\"抄表水费\",\"id\":\"1427808297385205763\",\"lastIndex\":30,\"deadline\":1632240000000,\"feeUser\":\"张三\"}],\"accountForms\":[],\"discountMoney\":\"0.00\",\"receivableMoney\":\"17173.95\",\"usePreSave\":false,\"usePrePay\":false,\"changeMoney\":\"0.00\",\"lateFeeMoney\":\"0.00\",\"payMoney\":\"17173.95\",\"prePayMoney\":\"0\",\"preSaveMoney\":\"0\",\"payMethod\":\"9d46e7f3729843ef8dc35f9987f7f2fe\",\"itemTotalMoney\":\"17173.950000000000000011102230246251565404236316680908203125\"}', '{\"code\":200,\"data\":{\"certificate\":\"\",\"changeMoney\":\"0.00\",\"comment\":\"\",\"creatorTime\":1629256756548,\"creatorUserId\":\"1\",\"discountMoney\":\"0.00\",\"feeType\":\"house\",\"id\":\"1427832441472557057\",\"itemTotalMoney\":\"17173.950000000000000011102230246251565404236316680908203125\",\"lateFeeMoney\":\"0.00\",\"name\":\"物业管理费,抄表水费等...\",\"payMethod\":\"9d46e7f3729843ef8dc35f9987f7f2fe\",\"payMoney\":\"17173.95\",\"payNo\":\"PAY2021081800204\",\"payTime\":1629256756541,\"payerIdcard\":\"140702195603032603\",\"payerName\":\"张三\",\"payerPhone\":\"13562356253\",\"prePayMoney\":\"0\",\"preSaveMoney\":\"0\",\"receivableMoney\":\"17173.95\",\"resourceName\":\"01-003\",\"type\":\"pay\"},\"msg\":\"Success\"}', '0', null, '2021-08-18 11:19:16');
INSERT INTO `sys_oper_log` VALUES ('347', '收费数据支付', '2', 'com.zhaoxinms.payment.controller.PaymentBillPayController.pay()', 'POST', '1', 'admin', null, '/payment/PaymentBillPay/payBill', '127.0.0.1', '内网IP', '{\"canUsePrePayMoney\":\"0\",\"paymentBills\":[{\"feeItemId\":\"6d81c6bc842e4d8ebfdfbd653b5e1f64\",\"payLogNo\":\"\",\"resourceId\":\"1466607490555764737\",\"endDate\":1655740800000,\"num\":\"290.00\",\"multiple\":1,\"discount\":\"10000.00\",\"payLogId\":\"\",\"resourceName\":\"01-004\",\"receivable\":\"9140.00\",\"currentIndex\":0,\"beginDate\":1624291200000,\"loss\":0,\"total\":\"19140.00\",\"price\":\"66.0000\",\"lateFee\":\"0\",\"feeItemName\":\"物业管理费\",\"id\":\"1416331589234794498\",\"lastIndex\":0,\"deadline\":1629129600000,\"feeUser\":\"张四\"},{\"feeItemId\":\"1334284783299108865\",\"payLogNo\":\"\",\"resourceId\":\"1466607490555764737\",\"endDate\":1625673600000,\"num\":\"1.10\",\"multiple\":1.1,\"discount\":\"0.00\",\"payLogId\":\"\",\"resourceName\":\"01-004\",\"receivable\":\"0.50\",\"currentIndex\":1,\"beginDate\":1624291200000,\"loss\":0,\"total\":\"0.50\",\"price\":\"0.4500\",\"lateFee\":\"0\",\"feeItemName\":\"抄表水费\",\"id\":\"1416334276873494529\",\"lastIndex\":0,\"deadline\":1629820800000,\"feeUser\":\"张四\"},{\"feeItemId\":\"99bcebcea39444bca429a18a413aabdd\",\"payLogNo\":\"\",\"resourceId\":\"1466607490555764737\",\"endDate\":1655740800000,\"num\":\"210000.00\",\"multiple\":1,\"discount\":\"0.00\",\"payLogId\":\"\",\"resourceName\":\"01-004\",\"receivable\":\"210000.00\",\"currentIndex\":0,\"beginDate\":1624291200000,\"loss\":0,\"total\":\"210000.00\",\"price\":\"1.0000\",\"lateFee\":\"0\",\"feeItemName\":\"房屋租金\",\"id\":\"1416334746941726722\",\"lastIndex\":0,\"deadline\":1628697600000,\"feeUser\":\"张四\"}],\"accountForms\":[],\"discountMoney\":\"10000.00\",\"receivableMoney\":\"219140.50\",\"usePreSave\":false,\"usePrePay\":false,\"changeMoney\":\"0.00\",\"lateFeeMoney\":\"0.00\",\"payMoney\":\"219140.50\",\"prePayMoney\":\"0\",\"preSaveMoney\":\"0\",\"payMethod\":\"9d46e7f3729843ef8dc35f9987f7f2fe\",\"itemTotalMoney\":\"229140.5\"}', '{\"code\":200,\"data\":{\"certificate\":\"\",\"changeMoney\":\"0.00\",\"comment\":\"\",\"creatorTime\":1629256866601,\"creatorUserId\":\"1\",\"discountMoney\":\"10000.00\",\"feeType\":\"house\",\"id\":\"1427832903064100866\",\"itemTotalMoney\":\"229140.5\",\"lateFeeMoney\":\"0.00\",\"name\":\"物业管理费,抄表水费等...\",\"payMethod\":\"9d46e7f3729843ef8dc35f9987f7f2fe\",\"payMoney\":\"219140.50\",\"payNo\":\"PAY2021081800205\",\"payTime\":1629256866589,\"payerIdcard\":\"140236198702023256\",\"payerName\":\"张四\",\"payerPhone\":\"13651325896\",\"prePayMoney\":\"0\",\"preSaveMoney\":\"0\",\"receivableMoney\":\"219140.50\",\"resourceName\":\"01-004\",\"type\":\"pay\"},\"msg\":\"Success\"}', '0', null, '2021-08-18 11:21:06');
INSERT INTO `sys_oper_log` VALUES ('348', '收费数据支付', '2', 'com.zhaoxinms.payment.controller.PaymentBillPayController.pay()', 'POST', '1', 'admin', null, '/payment/PaymentBillPay/payBill', '127.0.0.1', '内网IP', '{\"canUsePrePayMoney\":\"0\",\"paymentBills\":[{\"feeItemId\":\"6d81c6bc842e4d8ebfdfbd653b5e1f64\",\"payLogNo\":\"\",\"resourceId\":\"1466607490597707778\",\"endDate\":1656691200000,\"num\":\"320.00\",\"multiple\":1,\"discount\":\"0.00\",\"payLogId\":\"\",\"resourceName\":\"01-005\",\"receivable\":\"21120.00\",\"currentIndex\":0,\"beginDate\":1625241600000,\"loss\":0,\"total\":\"21120.00\",\"price\":\"66.0000\",\"lateFee\":\"0\",\"feeItemName\":\"物业管理费\",\"id\":\"1416326361370402819\",\"lastIndex\":0,\"deadline\":1629043200000,\"feeUser\":\"张武\"},{\"feeItemId\":\"1334284783299108865\",\"payLogNo\":\"\",\"resourceId\":\"1466607490597707778\",\"endDate\":1627747200000,\"num\":\"35.00\",\"multiple\":1,\"discount\":\"0.00\",\"payLogId\":\"\",\"resourceName\":\"01-005\",\"receivable\":\"15.75\",\"currentIndex\":35,\"beginDate\":1625241600000,\"loss\":0,\"total\":\"15.75\",\"price\":\"0.4500\",\"lateFee\":\"0\",\"feeItemName\":\"抄表水费\",\"id\":\"1416331156474896385\",\"lastIndex\":0,\"deadline\":1630339200000,\"feeUser\":\"张武\"}],\"accountForms\":[],\"discountMoney\":\"0.00\",\"receivableMoney\":\"21135.75\",\"usePreSave\":false,\"usePrePay\":false,\"changeMoney\":\"0.00\",\"lateFeeMoney\":\"0.00\",\"payMoney\":\"21135.75\",\"prePayMoney\":\"0\",\"preSaveMoney\":\"0\",\"payMethod\":\"9d46e7f3729843ef8dc35f9987f7f2fe\",\"itemTotalMoney\":\"21135.75\"}', '{\"code\":200,\"data\":{\"certificate\":\"\",\"changeMoney\":\"0.00\",\"comment\":\"\",\"creatorTime\":1629256921524,\"creatorUserId\":\"1\",\"discountMoney\":\"0.00\",\"feeType\":\"house\",\"id\":\"1427833133427859457\",\"itemTotalMoney\":\"21135.75\",\"lateFeeMoney\":\"0.00\",\"name\":\"物业管理费,抄表水费\",\"payMethod\":\"9d46e7f3729843ef8dc35f9987f7f2fe\",\"payMoney\":\"21135.75\",\"payNo\":\"PAY2021081800206\",\"payTime\":1629256921516,\"payerIdcard\":\"140702198702036505\",\"payerName\":\"张武\",\"payerPhone\":\"13684652365\",\"prePayMoney\":\"0\",\"preSaveMoney\":\"0\",\"receivableMoney\":\"21135.75\",\"resourceName\":\"01-005\",\"type\":\"pay\"},\"msg\":\"Success\"}', '0', null, '2021-08-18 11:22:01');
INSERT INTO `sys_oper_log` VALUES ('349', '抄表数据导入', '6', 'com.zhaoxinms.payment.controller.PaymentMeterImportController.importPreview()', 'GET', '1', 'admin', null, '/payment/PaymentMeterImport/Import', '127.0.0.1', '内网IP', '{}', '{\"code\":200,\"data\":{\"num\":\"10\"},\"msg\":\"Success\"}', '0', null, '2021-09-18 11:30:39');
INSERT INTO `sys_oper_log` VALUES ('350', '收费数据支付', '2', 'com.zhaoxinms.payment.controller.PaymentBillPayController.pay()', 'POST', '1', 'admin', null, '/payment/PaymentBillPay/payBill', '127.0.0.1', '内网IP', '{\"canUsePrePayMoney\":\"36.00\",\"paymentBills\":[{\"feeItemId\":\"1334284783299108865\",\"payLogNo\":\"\",\"resourceId\":\"1466607490492850177\",\"endDate\":1632931200000,\"num\":\"80.00\",\"multiple\":1,\"discount\":\"0.00\",\"payLogId\":\"\",\"resourceName\":\"01-001\",\"receivable\":\"36.00\",\"currentIndex\":200,\"beginDate\":1630425600000,\"loss\":0,\"total\":\"36.00\",\"price\":\"0.4500\",\"lateFee\":\"0\",\"feeItemName\":\"抄表水费\",\"id\":\"1439069430603587585\",\"lastIndex\":120,\"deadline\":1632931200000,\"feeUser\":\"张一\"}],\"accountForms\":[{\"feeItemId\":\"6d81c6bc842e4d8ebfdfbd653b5e1f64\",\"resourceId\":\"1466607490492850177\",\"payMoney\":\"0.00\",\"amt\":\"0.00\",\"block\":\"01\",\"id\":\"1411251277693788161\"},{\"feeItemId\":\"\",\"resourceId\":\"1466607490492850177\",\"payMoney\":\"36\",\"amt\":\"746.00\",\"block\":\"01\",\"id\":\"1416368225268080641\"}],\"discountMoney\":\"0.00\",\"receivableMoney\":\"36.00\",\"usePreSave\":false,\"usePrePay\":true,\"changeMoney\":\"0.00\",\"lateFeeMoney\":\"0.00\",\"payMoney\":\"0.00\",\"prePayMoney\":\"36.00\",\"preSaveMoney\":\"0\",\"payMethod\":\"9d46e7f3729843ef8dc35f9987f7f2fe\",\"itemTotalMoney\":\"36\"}', '{\"code\":200,\"data\":{\"certificate\":\"\",\"changeMoney\":\"0.00\",\"comment\":\"\",\"creatorTime\":1631935894926,\"creatorUserId\":\"1\",\"discountMoney\":\"0.00\",\"feeType\":\"house\",\"id\":\"1439069562287955970\",\"itemTotalMoney\":\"36\",\"lateFeeMoney\":\"0.00\",\"name\":\"抄表水费\",\"payMethod\":\"9d46e7f3729843ef8dc35f9987f7f2fe\",\"payMoney\":\"0.00\",\"payNo\":\"PAY2021091800201\",\"payTime\":1631935894919,\"payerIdcard\":\"140702198502036501\",\"payerName\":\"张一\",\"payerPhone\":\"13265412321\",\"prePayMoney\":\"36.00\",\"preSaveMoney\":\"0\",\"receivableMoney\":\"36.00\",\"resourceName\":\"01-001\",\"type\":\"pay\"},\"msg\":\"Success\"}', '0', null, '2021-09-18 11:31:34');
INSERT INTO `sys_oper_log` VALUES ('351', '收费数据支付', '2', 'com.zhaoxinms.payment.controller.PaymentBillPayController.pay()', 'POST', '1', 'admin', null, '/payment/PaymentBillPay/payBill', '127.0.0.1', '内网IP', '{\"canUsePrePayMoney\":\"0\",\"paymentBills\":[{\"feeItemId\":\"6d81c6bc842e4d8ebfdfbd653b5e1f64\",\"payLogNo\":\"\",\"resourceId\":\"1466607490526404610\",\"endDate\":1656691200000,\"num\":\"230.00\",\"multiple\":1,\"discount\":\"0.00\",\"payLogId\":\"\",\"resourceName\":\"01-002\",\"receivable\":\"15180.00\",\"currentIndex\":0,\"beginDate\":1625241600000,\"loss\":0,\"total\":\"15180.00\",\"price\":\"66.0000\",\"lateFee\":\"0\",\"feeItemName\":\"物业管理费\",\"id\":\"1416326361370402818\",\"lastIndex\":0,\"deadline\":1629043200000,\"feeUser\":\"张二\"},{\"feeItemId\":\"1334284783299108865\",\"payLogNo\":\"\",\"resourceId\":\"1466607490526404610\",\"endDate\":1627747200000,\"num\":\"150.00\",\"multiple\":1,\"discount\":\"0.00\",\"payLogId\":\"\",\"resourceName\":\"01-002\",\"receivable\":\"67.50\",\"currentIndex\":150,\"beginDate\":1625241600000,\"loss\":0,\"total\":\"67.50\",\"price\":\"0.4500\",\"lateFee\":\"0\",\"feeItemName\":\"抄表水费\",\"id\":\"1416331156466507778\",\"lastIndex\":0,\"deadline\":1630339200000,\"feeUser\":\"张二\"},{\"feeItemId\":\"99bcebcea39444bca429a18a413aabdd\",\"payLogNo\":\"\",\"resourceId\":\"1466607490526404610\",\"endDate\":1656691200000,\"num\":\"150000.00\",\"multiple\":1,\"discount\":\"0.00\",\"payLogId\":\"\",\"resourceName\":\"01-002\",\"receivable\":\"150000.00\",\"currentIndex\":0,\"beginDate\":1625241600000,\"loss\":0,\"total\":\"150000.00\",\"price\":\"1.0000\",\"lateFee\":\"0\",\"feeItemName\":\"房屋租金\",\"id\":\"1416334746950115329\",\"lastIndex\":0,\"deadline\":1628697600000,\"feeUser\":\"张二\"},{\"feeItemId\":\"1334284783299108865\",\"payLogNo\":\"\",\"resourceId\":\"1466607490526404610\",\"endDate\":1630339200000,\"num\":\"110.00\",\"multiple\":1,\"discount\":\"0.00\",\"payLogId\":\"\",\"resourceName\":\"01-002\",\"receivable\":\"49.50\",\"currentIndex\":110,\"beginDate\":1627833600000,\"loss\":0,\"total\":\"49.50\",\"price\":\"0.4500\",\"lateFee\":\"0\",\"feeItemName\":\"抄表水费\",\"id\":\"1427808297385205762\",\"lastIndex\":0,\"deadline\":1632240000000,\"feeUser\":\"张二\"},{\"feeItemId\":\"1334284783299108865\",\"payLogNo\":\"\",\"resourceId\":\"1466607490526404610\",\"endDate\":1632931200000,\"num\":\"210.00\",\"multiple\":1,\"discount\":\"0.00\",\"payLogId\":\"\",\"resourceName\":\"01-002\",\"receivable\":\"94.50\",\"currentIndex\":320,\"beginDate\":163042', null, '1', '支付方式不能为空', '2021-09-18 11:33:06');
INSERT INTO `sys_oper_log` VALUES ('352', '收费数据支付', '2', 'com.zhaoxinms.payment.controller.PaymentBillPayController.pay()', 'POST', '1', 'admin', null, '/payment/PaymentBillPay/payBill', '127.0.0.1', '内网IP', '{\"canUsePrePayMoney\":\"0\",\"paymentBills\":[{\"feeItemId\":\"6d81c6bc842e4d8ebfdfbd653b5e1f64\",\"payLogNo\":\"\",\"resourceId\":\"1466607490526404610\",\"endDate\":1656691200000,\"num\":\"230.00\",\"multiple\":1,\"discount\":\"0.00\",\"payLogId\":\"\",\"resourceName\":\"01-002\",\"receivable\":\"15180.00\",\"currentIndex\":0,\"beginDate\":1625241600000,\"loss\":0,\"total\":\"15180.00\",\"price\":\"66.0000\",\"lateFee\":\"0\",\"feeItemName\":\"物业管理费\",\"id\":\"1416326361370402818\",\"lastIndex\":0,\"deadline\":1629043200000,\"feeUser\":\"张二\"},{\"feeItemId\":\"1334284783299108865\",\"payLogNo\":\"\",\"resourceId\":\"1466607490526404610\",\"endDate\":1627747200000,\"num\":\"150.00\",\"multiple\":1,\"discount\":\"0.00\",\"payLogId\":\"\",\"resourceName\":\"01-002\",\"receivable\":\"67.50\",\"currentIndex\":150,\"beginDate\":1625241600000,\"loss\":0,\"total\":\"67.50\",\"price\":\"0.4500\",\"lateFee\":\"0\",\"feeItemName\":\"抄表水费\",\"id\":\"1416331156466507778\",\"lastIndex\":0,\"deadline\":1630339200000,\"feeUser\":\"张二\"},{\"feeItemId\":\"99bcebcea39444bca429a18a413aabdd\",\"payLogNo\":\"\",\"resourceId\":\"1466607490526404610\",\"endDate\":1656691200000,\"num\":\"150000.00\",\"multiple\":1,\"discount\":\"0.00\",\"payLogId\":\"\",\"resourceName\":\"01-002\",\"receivable\":\"150000.00\",\"currentIndex\":0,\"beginDate\":1625241600000,\"loss\":0,\"total\":\"150000.00\",\"price\":\"1.0000\",\"lateFee\":\"0\",\"feeItemName\":\"房屋租金\",\"id\":\"1416334746950115329\",\"lastIndex\":0,\"deadline\":1628697600000,\"feeUser\":\"张二\"},{\"feeItemId\":\"1334284783299108865\",\"payLogNo\":\"\",\"resourceId\":\"1466607490526404610\",\"endDate\":1630339200000,\"num\":\"110.00\",\"multiple\":1,\"discount\":\"0.00\",\"payLogId\":\"\",\"resourceName\":\"01-002\",\"receivable\":\"49.50\",\"currentIndex\":110,\"beginDate\":1627833600000,\"loss\":0,\"total\":\"49.50\",\"price\":\"0.4500\",\"lateFee\":\"0\",\"feeItemName\":\"抄表水费\",\"id\":\"1427808297385205762\",\"lastIndex\":0,\"deadline\":1632240000000,\"feeUser\":\"张二\"},{\"feeItemId\":\"1334284783299108865\",\"payLogNo\":\"\",\"resourceId\":\"1466607490526404610\",\"endDate\":1632931200000,\"num\":\"210.00\",\"multiple\":1,\"discount\":\"0.00\",\"payLogId\":\"\",\"resourceName\":\"01-002\",\"receivable\":\"94.50\",\"currentIndex\":320,\"beginDate\":163042', '{\"code\":200,\"data\":{\"certificate\":\"\",\"changeMoney\":\"0\",\"comment\":\"\",\"creatorTime\":1631935992802,\"creatorUserId\":\"1\",\"discountMoney\":\"0.00\",\"feeType\":\"house\",\"id\":\"1439069972805459969\",\"itemTotalMoney\":\"165391.5\",\"lateFeeMoney\":\"0.00\",\"name\":\"物业管理费,抄表水费等...\",\"payMethod\":\"744e2f652ad24b7eaa18d00cf134136a\",\"payMoney\":\"200000.00\",\"payNo\":\"PAY2021091800204\",\"payTime\":1631935992793,\"payerIdcard\":\"140702195603023202\",\"payerName\":\"张二\",\"payerPhone\":\"13561325653\",\"prePayMoney\":\"0\",\"preSaveMoney\":\"34608.50\",\"receivableMoney\":\"165391.50\",\"resourceName\":\"01-002\",\"type\":\"pay\"},\"msg\":\"Success\"}', '0', null, '2021-09-18 11:33:12');
INSERT INTO `sys_oper_log` VALUES ('353', '更新收费项', '2', 'com.zhaoxinms.baseconfig.controller.ConfigFeeItemController.update()', 'PUT', '1', 'admin', null, '/baseconfig/ConfigFeeItem/6d81c6bc842e4d8ebfdfbd653b5e1f64', '127.0.0.1', '内网IP', '6d81c6bc842e4d8ebfdfbd653b5e1f64 {\"lateFeeDays\":60,\"period\":12,\"formulaExpression\":\"\",\"price\":66,\"name\":\"物业管理费\",\"formula\":\"base\",\"id\":\"6d81c6bc842e4d8ebfdfbd653b5e1f64\",\"lateFeeRate\":\"0.6\",\"numType\":\"building_area\",\"type\":\"house\",\"lateFeeEnable\":1}', '{\"code\":200,\"msg\":\"更新成功\"}', '0', null, '2021-10-21 10:47:24');
INSERT INTO `sys_oper_log` VALUES ('354', '抄表数据导入', '6', 'com.zhaoxinms.payment.controller.PaymentMeterImportController.importPreview()', 'GET', '1', 'admin', null, '/payment/PaymentMeterImport/Import', '127.0.0.1', '内网IP', '{}', '{\"code\":200,\"data\":{\"num\":\"7\"},\"msg\":\"Success\"}', '0', null, '2021-10-21 11:13:41');
INSERT INTO `sys_oper_log` VALUES ('355', '菜单管理', '2', 'com.zhaoxinms.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"query\":\"\",\"icon\":\"guide\",\"orderNum\":\"40\",\"menuName\":\"肇新智慧物业\",\"params\":{},\"parentId\":0,\"isCache\":\"0\",\"path\":\"http://zhaoxinms.com:81\",\"children\":[],\"createTime\":1636626924000,\"updateBy\":\"admin\",\"isFrame\":\"0\",\"menuId\":4,\"menuType\":\"M\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2021-12-09 11:57:28');

-- ----------------------------
-- Table structure for `sys_post`
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post` (
  `post_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) NOT NULL COMMENT '岗位名称',
  `post_sort` int(4) NOT NULL COMMENT '显示顺序',
  `status` char(1) NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='岗位信息表';

-- ----------------------------
-- Records of sys_post
-- ----------------------------
INSERT INTO `sys_post` VALUES ('1', 'ceo', '董事长', '1', '0', 'admin', '2021-11-11 18:35:24', '', null, '');
INSERT INTO `sys_post` VALUES ('2', 'se', '项目经理', '2', '0', 'admin', '2021-11-11 18:35:24', '', null, '');
INSERT INTO `sys_post` VALUES ('3', 'hr', '人力资源', '3', '0', 'admin', '2021-11-11 18:35:24', '', null, '');
INSERT INTO `sys_post` VALUES ('4', 'user', '普通员工', '4', '0', 'admin', '2021-11-11 18:35:24', '', null, '');

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(4) NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `menu_check_strictly` tinyint(1) DEFAULT '1' COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) DEFAULT '1' COMMENT '部门树选择项是否关联显示',
  `status` char(1) NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='角色信息表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '超级管理员', 'admin', '1', '1', '1', '1', '0', '0', 'admin', '2021-11-11 18:35:24', '', null, '超级管理员');
INSERT INTO `sys_role` VALUES ('2', '普通角色', 'common', '2', '2', '1', '1', '0', '0', 'admin', '2021-11-11 18:35:24', '', '2021-12-01 10:29:30', '普通角色');
INSERT INTO `sys_role` VALUES ('3', '管理员', 'manager', '0', '1', '1', '1', '0', '0', 'admin', '2021-11-30 17:59:47', 'admin', '2021-12-01 11:28:41', null);
INSERT INTO `sys_role` VALUES ('4', '收费员', 'payee', '0', '1', '1', '1', '0', '0', 'admin', '2021-11-30 18:53:50', 'admin', '2021-12-01 11:10:09', null);
INSERT INTO `sys_role` VALUES ('5', '财务人员', 'finance', '0', '1', '1', '1', '0', '0', 'admin', '2021-12-01 11:11:19', '', null, null);

-- ----------------------------
-- Table structure for `sys_role_dept`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `dept_id` bigint(20) NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`,`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色和部门关联表';

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色和菜单关联表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('2', '1');
INSERT INTO `sys_role_menu` VALUES ('2', '2');
INSERT INTO `sys_role_menu` VALUES ('2', '3');
INSERT INTO `sys_role_menu` VALUES ('2', '4');
INSERT INTO `sys_role_menu` VALUES ('2', '100');
INSERT INTO `sys_role_menu` VALUES ('2', '101');
INSERT INTO `sys_role_menu` VALUES ('2', '102');
INSERT INTO `sys_role_menu` VALUES ('2', '103');
INSERT INTO `sys_role_menu` VALUES ('2', '104');
INSERT INTO `sys_role_menu` VALUES ('2', '105');
INSERT INTO `sys_role_menu` VALUES ('2', '106');
INSERT INTO `sys_role_menu` VALUES ('2', '107');
INSERT INTO `sys_role_menu` VALUES ('2', '108');
INSERT INTO `sys_role_menu` VALUES ('2', '109');
INSERT INTO `sys_role_menu` VALUES ('2', '110');
INSERT INTO `sys_role_menu` VALUES ('2', '111');
INSERT INTO `sys_role_menu` VALUES ('2', '112');
INSERT INTO `sys_role_menu` VALUES ('2', '113');
INSERT INTO `sys_role_menu` VALUES ('2', '114');
INSERT INTO `sys_role_menu` VALUES ('2', '115');
INSERT INTO `sys_role_menu` VALUES ('2', '116');
INSERT INTO `sys_role_menu` VALUES ('2', '500');
INSERT INTO `sys_role_menu` VALUES ('2', '501');
INSERT INTO `sys_role_menu` VALUES ('2', '1000');
INSERT INTO `sys_role_menu` VALUES ('2', '1001');
INSERT INTO `sys_role_menu` VALUES ('2', '1002');
INSERT INTO `sys_role_menu` VALUES ('2', '1003');
INSERT INTO `sys_role_menu` VALUES ('2', '1004');
INSERT INTO `sys_role_menu` VALUES ('2', '1005');
INSERT INTO `sys_role_menu` VALUES ('2', '1006');
INSERT INTO `sys_role_menu` VALUES ('2', '1007');
INSERT INTO `sys_role_menu` VALUES ('2', '1008');
INSERT INTO `sys_role_menu` VALUES ('2', '1009');
INSERT INTO `sys_role_menu` VALUES ('2', '1010');
INSERT INTO `sys_role_menu` VALUES ('2', '1011');
INSERT INTO `sys_role_menu` VALUES ('2', '1012');
INSERT INTO `sys_role_menu` VALUES ('2', '1013');
INSERT INTO `sys_role_menu` VALUES ('2', '1014');
INSERT INTO `sys_role_menu` VALUES ('2', '1015');
INSERT INTO `sys_role_menu` VALUES ('2', '1016');
INSERT INTO `sys_role_menu` VALUES ('2', '1017');
INSERT INTO `sys_role_menu` VALUES ('2', '1018');
INSERT INTO `sys_role_menu` VALUES ('2', '1019');
INSERT INTO `sys_role_menu` VALUES ('2', '1020');
INSERT INTO `sys_role_menu` VALUES ('2', '1021');
INSERT INTO `sys_role_menu` VALUES ('2', '1022');
INSERT INTO `sys_role_menu` VALUES ('2', '1023');
INSERT INTO `sys_role_menu` VALUES ('2', '1024');
INSERT INTO `sys_role_menu` VALUES ('2', '1025');
INSERT INTO `sys_role_menu` VALUES ('2', '1026');
INSERT INTO `sys_role_menu` VALUES ('2', '1027');
INSERT INTO `sys_role_menu` VALUES ('2', '1028');
INSERT INTO `sys_role_menu` VALUES ('2', '1029');
INSERT INTO `sys_role_menu` VALUES ('2', '1030');
INSERT INTO `sys_role_menu` VALUES ('2', '1031');
INSERT INTO `sys_role_menu` VALUES ('2', '1032');
INSERT INTO `sys_role_menu` VALUES ('2', '1033');
INSERT INTO `sys_role_menu` VALUES ('2', '1034');
INSERT INTO `sys_role_menu` VALUES ('2', '1035');
INSERT INTO `sys_role_menu` VALUES ('2', '1036');
INSERT INTO `sys_role_menu` VALUES ('2', '1037');
INSERT INTO `sys_role_menu` VALUES ('2', '1038');
INSERT INTO `sys_role_menu` VALUES ('2', '1039');
INSERT INTO `sys_role_menu` VALUES ('2', '1040');
INSERT INTO `sys_role_menu` VALUES ('2', '1041');
INSERT INTO `sys_role_menu` VALUES ('2', '1042');
INSERT INTO `sys_role_menu` VALUES ('2', '1043');
INSERT INTO `sys_role_menu` VALUES ('2', '1044');
INSERT INTO `sys_role_menu` VALUES ('2', '1045');
INSERT INTO `sys_role_menu` VALUES ('2', '1046');
INSERT INTO `sys_role_menu` VALUES ('2', '1047');
INSERT INTO `sys_role_menu` VALUES ('2', '1048');
INSERT INTO `sys_role_menu` VALUES ('2', '1049');
INSERT INTO `sys_role_menu` VALUES ('2', '1050');
INSERT INTO `sys_role_menu` VALUES ('2', '1051');
INSERT INTO `sys_role_menu` VALUES ('2', '1052');
INSERT INTO `sys_role_menu` VALUES ('2', '1053');
INSERT INTO `sys_role_menu` VALUES ('2', '1054');
INSERT INTO `sys_role_menu` VALUES ('2', '1055');
INSERT INTO `sys_role_menu` VALUES ('2', '1056');
INSERT INTO `sys_role_menu` VALUES ('2', '1057');
INSERT INTO `sys_role_menu` VALUES ('2', '1058');
INSERT INTO `sys_role_menu` VALUES ('2', '1059');
INSERT INTO `sys_role_menu` VALUES ('2', '1060');
INSERT INTO `sys_role_menu` VALUES ('3', '1');
INSERT INTO `sys_role_menu` VALUES ('3', '2');
INSERT INTO `sys_role_menu` VALUES ('3', '3');
INSERT INTO `sys_role_menu` VALUES ('3', '4');
INSERT INTO `sys_role_menu` VALUES ('3', '100');
INSERT INTO `sys_role_menu` VALUES ('3', '101');
INSERT INTO `sys_role_menu` VALUES ('3', '102');
INSERT INTO `sys_role_menu` VALUES ('3', '103');
INSERT INTO `sys_role_menu` VALUES ('3', '104');
INSERT INTO `sys_role_menu` VALUES ('3', '105');
INSERT INTO `sys_role_menu` VALUES ('3', '106');
INSERT INTO `sys_role_menu` VALUES ('3', '107');
INSERT INTO `sys_role_menu` VALUES ('3', '108');
INSERT INTO `sys_role_menu` VALUES ('3', '109');
INSERT INTO `sys_role_menu` VALUES ('3', '110');
INSERT INTO `sys_role_menu` VALUES ('3', '111');
INSERT INTO `sys_role_menu` VALUES ('3', '112');
INSERT INTO `sys_role_menu` VALUES ('3', '113');
INSERT INTO `sys_role_menu` VALUES ('3', '114');
INSERT INTO `sys_role_menu` VALUES ('3', '115');
INSERT INTO `sys_role_menu` VALUES ('3', '116');
INSERT INTO `sys_role_menu` VALUES ('3', '500');
INSERT INTO `sys_role_menu` VALUES ('3', '501');
INSERT INTO `sys_role_menu` VALUES ('3', '1001');
INSERT INTO `sys_role_menu` VALUES ('3', '1002');
INSERT INTO `sys_role_menu` VALUES ('3', '1003');
INSERT INTO `sys_role_menu` VALUES ('3', '1004');
INSERT INTO `sys_role_menu` VALUES ('3', '1005');
INSERT INTO `sys_role_menu` VALUES ('3', '1006');
INSERT INTO `sys_role_menu` VALUES ('3', '1007');
INSERT INTO `sys_role_menu` VALUES ('3', '1008');
INSERT INTO `sys_role_menu` VALUES ('3', '1009');
INSERT INTO `sys_role_menu` VALUES ('3', '1010');
INSERT INTO `sys_role_menu` VALUES ('3', '1011');
INSERT INTO `sys_role_menu` VALUES ('3', '1012');
INSERT INTO `sys_role_menu` VALUES ('3', '1013');
INSERT INTO `sys_role_menu` VALUES ('3', '1014');
INSERT INTO `sys_role_menu` VALUES ('3', '1015');
INSERT INTO `sys_role_menu` VALUES ('3', '1016');
INSERT INTO `sys_role_menu` VALUES ('3', '1017');
INSERT INTO `sys_role_menu` VALUES ('3', '1018');
INSERT INTO `sys_role_menu` VALUES ('3', '1019');
INSERT INTO `sys_role_menu` VALUES ('3', '1020');
INSERT INTO `sys_role_menu` VALUES ('3', '1021');
INSERT INTO `sys_role_menu` VALUES ('3', '1022');
INSERT INTO `sys_role_menu` VALUES ('3', '1023');
INSERT INTO `sys_role_menu` VALUES ('3', '1024');
INSERT INTO `sys_role_menu` VALUES ('3', '1025');
INSERT INTO `sys_role_menu` VALUES ('3', '1026');
INSERT INTO `sys_role_menu` VALUES ('3', '1027');
INSERT INTO `sys_role_menu` VALUES ('3', '1028');
INSERT INTO `sys_role_menu` VALUES ('3', '1029');
INSERT INTO `sys_role_menu` VALUES ('3', '1030');
INSERT INTO `sys_role_menu` VALUES ('3', '1031');
INSERT INTO `sys_role_menu` VALUES ('3', '1032');
INSERT INTO `sys_role_menu` VALUES ('3', '1033');
INSERT INTO `sys_role_menu` VALUES ('3', '1034');
INSERT INTO `sys_role_menu` VALUES ('3', '1035');
INSERT INTO `sys_role_menu` VALUES ('3', '1036');
INSERT INTO `sys_role_menu` VALUES ('3', '1037');
INSERT INTO `sys_role_menu` VALUES ('3', '1038');
INSERT INTO `sys_role_menu` VALUES ('3', '1039');
INSERT INTO `sys_role_menu` VALUES ('3', '1040');
INSERT INTO `sys_role_menu` VALUES ('3', '1041');
INSERT INTO `sys_role_menu` VALUES ('3', '1042');
INSERT INTO `sys_role_menu` VALUES ('3', '1043');
INSERT INTO `sys_role_menu` VALUES ('3', '1044');
INSERT INTO `sys_role_menu` VALUES ('3', '1045');
INSERT INTO `sys_role_menu` VALUES ('3', '1046');
INSERT INTO `sys_role_menu` VALUES ('3', '1047');
INSERT INTO `sys_role_menu` VALUES ('3', '1048');
INSERT INTO `sys_role_menu` VALUES ('3', '1049');
INSERT INTO `sys_role_menu` VALUES ('3', '1050');
INSERT INTO `sys_role_menu` VALUES ('3', '1051');
INSERT INTO `sys_role_menu` VALUES ('3', '1052');
INSERT INTO `sys_role_menu` VALUES ('3', '1053');
INSERT INTO `sys_role_menu` VALUES ('3', '1054');
INSERT INTO `sys_role_menu` VALUES ('3', '1055');
INSERT INTO `sys_role_menu` VALUES ('3', '1056');
INSERT INTO `sys_role_menu` VALUES ('3', '1057');
INSERT INTO `sys_role_menu` VALUES ('3', '1058');
INSERT INTO `sys_role_menu` VALUES ('3', '1059');
INSERT INTO `sys_role_menu` VALUES ('3', '1060');
INSERT INTO `sys_role_menu` VALUES ('3', '2002');
INSERT INTO `sys_role_menu` VALUES ('3', '2003');
INSERT INTO `sys_role_menu` VALUES ('3', '2004');
INSERT INTO `sys_role_menu` VALUES ('3', '2005');
INSERT INTO `sys_role_menu` VALUES ('3', '2006');
INSERT INTO `sys_role_menu` VALUES ('3', '2007');
INSERT INTO `sys_role_menu` VALUES ('3', '2008');
INSERT INTO `sys_role_menu` VALUES ('3', '2009');
INSERT INTO `sys_role_menu` VALUES ('3', '2013');
INSERT INTO `sys_role_menu` VALUES ('3', '2014');
INSERT INTO `sys_role_menu` VALUES ('3', '2015');
INSERT INTO `sys_role_menu` VALUES ('3', '2016');
INSERT INTO `sys_role_menu` VALUES ('3', '2018');
INSERT INTO `sys_role_menu` VALUES ('3', '2019');
INSERT INTO `sys_role_menu` VALUES ('3', '2020');
INSERT INTO `sys_role_menu` VALUES ('3', '2021');
INSERT INTO `sys_role_menu` VALUES ('3', '2022');
INSERT INTO `sys_role_menu` VALUES ('4', '4');
INSERT INTO `sys_role_menu` VALUES ('4', '2000');
INSERT INTO `sys_role_menu` VALUES ('4', '2001');
INSERT INTO `sys_role_menu` VALUES ('4', '2010');
INSERT INTO `sys_role_menu` VALUES ('4', '2011');
INSERT INTO `sys_role_menu` VALUES ('4', '2012');
INSERT INTO `sys_role_menu` VALUES ('4', '2013');
INSERT INTO `sys_role_menu` VALUES ('4', '2015');
INSERT INTO `sys_role_menu` VALUES ('4', '2021');
INSERT INTO `sys_role_menu` VALUES ('5', '2013');
INSERT INTO `sys_role_menu` VALUES ('5', '2014');
INSERT INTO `sys_role_menu` VALUES ('5', '2015');
INSERT INTO `sys_role_menu` VALUES ('5', '2016');
INSERT INTO `sys_role_menu` VALUES ('5', '2018');
INSERT INTO `sys_role_menu` VALUES ('5', '2019');
INSERT INTO `sys_role_menu` VALUES ('5', '2020');
INSERT INTO `sys_role_menu` VALUES ('5', '2021');
INSERT INTO `sys_role_menu` VALUES ('5', '2022');

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `user_name` varchar(30) NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) DEFAULT '' COMMENT '手机号码',
  `sex` char(1) DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) DEFAULT '' COMMENT '密码',
  `status` char(1) DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(128) DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '100', 'admin', '肇新物业', '00', 'f545042301@163.com', '18306806281', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', '2021-12-11 11:57:35', 'admin', '2021-11-11 18:35:24', '', '2021-12-11 11:57:35', '管理员');
INSERT INTO `sys_user` VALUES ('3', '100', 'payee', '李四', '00', '', '', '0', '', '$2a$10$/5eXAPSba0q0TJ1R.gdYCO5JKOgPs2YcIBr7Z596eZnVKpA5M/M7i', '0', '0', '127.0.0.1', '2021-12-01 11:07:10', 'admin', '2021-11-30 17:00:44', '', '2021-12-01 11:07:10', null);
INSERT INTO `sys_user` VALUES ('4', '100', 'finance', '财务人员', '00', '', '', '0', '', '$2a$10$E4b04cnxT/pkTBYsoQRT/OxlJugehiyx2OGF8e0wJ4l70AIta/Blm', '0', '0', '127.0.0.1', '2021-12-01 11:20:25', 'admin', '2021-12-01 11:14:31', 'admin', '2021-12-01 11:20:25', null);
INSERT INTO `sys_user` VALUES ('5', '100', 'manager', '管理员', '00', '', '', '0', '', '$2a$10$UQHu72ceDt2t6JRwsWylnOEN1duS45x4DVBZPXUrJI.vBTbZ0lnzm', '0', '0', '127.0.0.1', '2021-12-01 14:15:47', 'admin', '2021-12-01 11:15:46', '', '2021-12-01 14:15:46', null);

-- ----------------------------
-- Table structure for `sys_user_post`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `post_id` bigint(20) NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`,`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户与岗位关联表';

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------
INSERT INTO `sys_user_post` VALUES ('1', '1');

-- ----------------------------
-- Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户和角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1');
INSERT INTO `sys_user_role` VALUES ('3', '4');
INSERT INTO `sys_user_role` VALUES ('4', '5');
INSERT INTO `sys_user_role` VALUES ('5', '3');
