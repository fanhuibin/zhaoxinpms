<template>
    <el-dialog
        :title="'打印'"
        :close-on-click-modal="false"
        :visible.sync="visible"
        class="Jdialog Jdialog_center"
        width="1000px"
        :before-close="close"
    >
        <iframe width="100%" height="100%" style="min-height: 600px" :src="url" frameborder="0"></iframe>
    </el-dialog>
</template>

<script>
import { getToken } from '@/utils/auth';
export default {
    data() {
        return {
            url: '',
            visible: false,
        };
    },
    mounted() {
        window.addEventListener('message', this.handleMessage);
    },
    methods: {
        init(url) {
            this.visible = true;
            this.$nextTick(() => {
                const token = getToken();
                this.url = url + `&token=${token}&page=1`;
            });
        },
        close(done) {
            this.$emit('close');
            done();
        },
        async handleMessage(e) {
            const data = e.data;
            if (data === 'closeDialog') {
                this.url = '';
                this.$emit('close');
            }
        },
    },
};
</script>
