function ityzl_SHOW_LOAD_LAYER(){  
            return layer.msg('加载中...', {icon: 16,shade: [0.5, '#f5f5f5'],scrollbar: false,offset: '0px', time:1000000}) ;  
};
function ityzl_CLOSE_LOAD_LAYER(index){  
        layer.close(index);  
};