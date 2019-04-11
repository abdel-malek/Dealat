//
//  VerificationVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 3/4/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit

class VerificationVC: BaseVC {

    @IBOutlet weak var tfCode: UITextField!
    
    @IBOutlet weak var lbl: UILabel!
    @IBOutlet weak var lblPhone: UILabel!
    @IBOutlet weak var btnEditPhone: UIButton!
    @IBOutlet weak var btnResend: UIButton!
    @IBOutlet weak var btnSkip: UIButton!

    var timer = Timer()

    let att : [NSAttributedString.Key: Any] = [
        NSAttributedString.Key.font : Theme.Font.Calibri.withSize(20),
        NSAttributedString.Key.foregroundColor : Theme.Color.White,
        NSAttributedString.Key.underlineStyle : NSUnderlineStyle.single.rawValue]
    
    let a2 : [NSAttributedString.Key: Any] = [
        NSAttributedString.Key.font : Theme.Font.Calibri.withSize(20),
        NSAttributedString.Key.foregroundColor : Theme.Color.White]

    
    override func viewDidLoad() {
        super.viewDidLoad()

        self.tfCode.placeHolderColor = Theme.Color.White
        self.lblPhone.text = User.getCurrentUser().phone
        
        let att1 = NSMutableAttributedString(string: "Skip".localized,
                                                        attributes: att)
        btnSkip.setAttributedTitle(att1, for: .normal)

        
        Provider.setScreenName("VerificationActivity")
        
        setupTimer()

    }
    
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        
        self.navigationController?.setNavigationBarHidden(true, animated: false)
    }

    
    @IBAction func verificationAction(){
        
        let code = tfCode.text!
        
        if code.isEmpty{
            self.showErrorMessage(text: "Please enter code")
        }else{
            
            if User.getCurrentUser().server_key != nil{
                let alert = UIAlertController.init(title: "Alert!".localized, message: "previousRegister".localized, preferredStyle: .alert)
                alert.addAction(UIAlertAction.init(title: "yes".localized, style: .default, handler: { (ac) in
                    self.sendVerificationCode(code: code, is_multi: 1)
                }))
                alert.addAction(UIAlertAction.init(title: "no".localized, style: .default, handler: { (ac) in
                    self.sendVerificationCode(code: code, is_multi: 0)
                }))
                self.present(alert, animated: true, completion: nil)
            }else{
                let me = User.getCurrentUser()
                me.isFirst = true
                User.saveMe(me: me)
                self.sendVerificationCode(code: code, is_multi: 0)
            }
        }
        
    }
    
    
    func setupTimer(){
        timerVerify()
        
        if Provider.getTimer() != 0{
            timer = Timer.scheduledTimer(timeInterval: 1, target: self, selector: #selector(self.timerVerify), userInfo: nil, repeats: true)
        }
    }
    
   @objc func timerVerify(){
        if Provider.getTimer() != 0{
            self.btnResend.isEnabled = false
            UIView.performWithoutAnimation {
                let s = Provider.getTimer()
            
                let att1 = NSMutableAttributedString(string: "\(s / 60) \("min".localized) - \(s % 60) \("sec".localized)",attributes: a2)
                btnResend.setAttributedTitle(att1, for: .normal)

                self.btnResend.layoutIfNeeded()
                self.btnEditPhone.isHidden = true
            }
            
        }else{
            self.btnResend.isEnabled = true
//            self.btnResend.setTitle("resendcode".localized, for: .normal)
            let att1 = NSMutableAttributedString(string: "resendcode".localized,
                                                 attributes: att)
            btnResend.setAttributedTitle(att1, for: .normal)
            self.btnEditPhone.isHidden = false

            self.timer.invalidate()
        }
    }
    
    @IBAction func resendCode(){
        
//        Provider.addTimer(1)
//
//        self.setupTimer()
//        return
        
        self.showLoading()
        Communication.shared.users_register(phone: User.getCurrentUser().phone, name: User.getCurrentUser().name) { (res) in
            self.hideLoading()

            self.setupTimer()
        }
    }

    @IBAction func changeNumber(){
        let me = User.getCurrentUser()
        me.statues_key = User.USER_STATUES.NEW_USER.rawValue
        User.saveMe(me: me)
        AppDelegate.setupViews()
    }
    
    func sendVerificationCode(code : String,is_multi : Int){
        self.showLoading()
        Communication.shared.verify(code : code,is_multi : is_multi, callback: { (res) in
            self.hideLoading()
//            Provider.goToHome()
            AppDelegate.setupViews()
        })
    }
    
    @IBAction func skipAction(){
        Provider.goToHome()
    }

}
