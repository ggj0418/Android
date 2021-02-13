var inicis_script = {
    get_inicis_webview: function(merchant_uid, pay_method, name, amount, buyer_email, buyer_name) {
        var IMP = window.IMP;
        IMP.init('imp04382526'); // 'iamport' 대신 부여받은 "가맹점 식별코드"를 사용

        //onclick, onload 등 원하는 이벤트에 호출합니다
        IMP.request_pay({
            pg : 'html5_inicis', // version 1.1.0부터 지원.
            pay_method : pay_method,
            merchant_uid : merchant_uid,
            name : name,
            amount : amount,
            buyer_email : buyer_email,
            buyer_name : buyer_name,
//            buyer_tel : buyer_tel,
            m_redirect_url : 'http://52.78.123.79/payments/certification',
            app_scheme : 'iamportapp'
        }, function(rsp) {``
            if ( rsp.success ) {
                var msg = '결제가 완료되었습니다.';
                msg += '고유ID : ' + rsp.imp_uid;
                msg += '상점 거래ID : ' + rsp.merchant_uid;
                msg += '결제 금액 : ' + rsp.paid_amount;
                msg += '카드 승인번호 : ' + rsp.apply_num;
            } else {
                var msg = '결제에 실패하였습니다.';
                msg += '에러내용 : ' + rsp.error_msg;
            }

            alert(msg);
        });
    }
}