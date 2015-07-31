describe('filters', function() {
    beforeEach(module('common.skype'));

    describe('skype', function() {
        it("should format a phone number to the logical skype version",  inject(function(skypeFilter) {
            var phones = [
                234234234,
                '4253333333',
                '+1425 234 2342',
                '(234) 234 2342',
                '234/-234-2342',
                '1425 333 2342'
            ];

            _.each(phones, function(phone, key) {
                var strippedPhone = String(phone).replace(/\D/g, '');

                if (strippedPhone[0] == '1') {
                    strippedPhone = strippedPhone.substr(1);
                }

                expect(skypeFilter(phone)).toBe('skype:+1' + strippedPhone + '?call');
            });
        }));
    });
});