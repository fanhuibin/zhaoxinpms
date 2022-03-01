<template>
    <transition name="el-zoom-in-center">
        <div class="Jpreview-main flow-form-main nohead">
            <div class="btns">
                <el-button>返 回</el-button>
            </div>
            <el-tabs class="Jel_tabs" style="padding: 0 10px">
                <el-tab-pane label="业主信息">
                    <div class="app-container">
                        <el-row :gutter="20">
                            <el-col :span="6" :xs="24">
                                <el-card class="box-card">
                                    <div slot="header" class="clearfix">
                                        <span>业主信息</span>
                                    </div>
                                    <div>
                                        <div class="text-center">
                                            <userAvatar :user="user" />
                                        </div>
                                        <ul class="list-group list-group-striped">
                                            <li class="list-group-item">
                                                <svg-icon icon-class="user" />
                                                业主姓名
                                                <div class="pull-right">{{ user.userName }}</div>
                                            </li>
                                            <li class="list-group-item">
                                                <svg-icon icon-class="phone" />
                                                手机号码
                                                <div class="pull-right">{{ user.phonenumber }}</div>
                                            </li>
                                            <li class="list-group-item">
                                                <i class="icon-item el-icon-postcard" style="font-size:20px;margin-right:10px;"/>
                                                身份证号
                                                <div class="pull-right">{{ user.idcard }}</div>
                                            </li>
                                            <li class="list-group-item">
                                                <svg-icon icon-class="email" />
                                                用户邮箱
                                                <div class="pull-right">{{ user.email }}</div>
                                            </li>
                                            <li class="list-group-item">
                                                <svg-icon icon-class="date" />
                                                创建日期
                                                <div class="pull-right">{{ parseTime(user.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</div>
                                            </li>
                                        </ul>
                                    </div>
                                </el-card>
                            </el-col>
                            <el-col :span="18" :xs="24">
                                <el-card>
                                    <div slot="header" class="clearfix">
                                        <span>基本资料</span>
                                    </div>
                                    <el-tabs v-model="activeTab">
                                        <el-tab-pane label="商铺信息" name="houseInfo">
                                            <userInfo :user="user" />
                                        </el-tab-pane>
                                        <el-tab-pane label="商铺历史信息" name="houseHistory">
                                            <userInfo :user="user" />
                                        </el-tab-pane>
                                        <el-tab-pane label="待缴费信息" name="fee">
                                            <resetPwd :user="user" />
                                        </el-tab-pane>
                                        <el-tab-pane label="缴费历史信息" name="feeHistory">
                                            <resetPwd :user="user" />
                                        </el-tab-pane>
                                    </el-tabs>
                                </el-card>
                            </el-col>
                        </el-row>
                    </div>
                </el-tab-pane>
            </el-tabs>
        </div>
    </transition>
</template>

<script>
import userAvatar from './userAvatar';
import userInfo from './userInfo';
import resetPwd from './resetPwd';
import { listOwnerUser, getOwnerUser, delOwnerUser, addOwnerUser, updateOwnerUser, exportOwnerUser } from '@/api/owner/ownerUser';


export default {
    name: 'Profile',
    components: { userAvatar, userInfo, resetPwd },
    data() {
        return {
            user: {},
            activeTab: 'houseInfo',
        };
    },
    created() {
        
    },
    methods: {
        init(ownerUser) {
            this.user = ownerUser;
        },
    },
};
</script>
