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
    
    override func viewDidLoad() {
        super.viewDidLoad()

        self.tfCode.placeHolderColor = Theme.Color.White
        
        Provider.setScreenName("VerificationActivity")

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
                self.sendVerificationCode(code: code, is_multi: 0)
            }
        }
        
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
