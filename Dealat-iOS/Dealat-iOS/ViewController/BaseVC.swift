//
//  BaseVC.swift
//  Dealat-iOS
//
//  Created by IOS Developer on 2/6/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import Foundation
import UIKit
import JGProgressHUD
import KSToastView

class BaseVC: UIViewController,UITextFieldDelegate, UITextViewDelegate, UIGestureRecognizerDelegate {
    
    var hud = JGProgressHUD.init(style: JGProgressHUDStyle.extraLight)
    var ref = UIRefreshControl()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        setupViews()
        configureNavigationBar()
        
        registerReceivers()
        
        self.view.endEditing(true)
        
        let tap = UITapGestureRecognizer(target: self, action: #selector(self.onTouch))
        tap.cancelsTouchesInView = false
        self.view.addGestureRecognizer(tap)
        
        self.ref.addTarget(self, action: #selector(getRefreshing), for: .valueChanged)
    }
    
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        
        //        self.tabBarController?.tabBar.isHidden = false
        
        unregisterReceivers()
        registerReceivers()
        
        //        self.navigationController?.setNavigationBarHidden(false, animated: true)
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        unregisterReceivers()
    }
    
    
    func registerReceivers()
    {
        notific.addObserver(self, selector: #selector(self.connectionErrorNotificationReceived(_:)), name: _ConnectionErrorNotification.not, object: nil)
        
        notific.addObserver(self, selector: #selector(self.RequestErrorNotificationRecived(_:)), name: _RequestErrorNotificationReceived.not, object: nil)
        
    }
    
    func unregisterReceivers()
    {
        notific.removeObserver(self, name: _ConnectionErrorNotification.not, object: nil)
        
        notific.removeObserver(self, name: _RequestErrorNotificationReceived.not, object: nil)
    }
    
    
    @objc func connectionErrorNotificationReceived(_ notification: NSNotification) {
        self.hideLoading()
        
        print("connectionErrorNotificationReceived")
        if let msg = notification.object as? String{
            self.showErrorMessage(text : msg)
        }
        if let err = notification.object as? Error{
            self.showErrorMessage(text : err.localizedDescription)
        }
    }
    
    @objc func RequestErrorNotificationRecived(_ notification : NSNotification)
    {
        print("RequestErrorNotificationRecived")
        self.hideLoading()
        
        
        if let msg = notification.object as? String
        {
            self.showErrorMessage(text: msg)
        }
    }
    
    
    func showErrorMessage(text: String) {
        KSToastView.ks_showToast(text, duration: 3)
        //        self.view.makeToast(text, duration: 1, position: ToastPosition.center)
        //        self.view.makeToast(text)
        
        //        self.view.makeToast(text, duration: 1, position: CGPoint.init(x: self.view.center.x, y: self.view.frame.height - 40), style: nil)
        
    }
    
    func setupViews(){
        
    }
    
    @objc func getRefreshing(){
        self.hideLoading()
        
    }
    
    func getData(){
        self.showLoading()
        getRefreshing()
    }
    
    func configureNavigationBar() {
        //        if let nav = self.navigationController{
        //            nav.isNavigationBarHidden = false
        
        //setup back button
        self.navigationItem.hidesBackButton = false
        let rr = UIBarButtonItem(title: "", style: UIBarButtonItemStyle.plain, target: nil, action:nil)
        self.navigationItem.backBarButtonItem = rr
        //        }
        
    }
    
    @objc func navigateBack() {
        let _ = self.navigationController?.popViewController(animated: true)
    }
    
    @objc @IBAction func dissmiss(){
        self.dismiss(animated: true, completion: nil)
    }
    
    func showLoading(){
        //        self.loadingView = MBProgressHUD.showAdded(to: self.view, animated: true)
        //        self.loadingView.bezelView.backgroundColor = Theme.Color.header
        //        self.loadingView.contentColor = .white
        
        hud.show(in: self.view, animated: false)
        hud.shadow = JGProgressHUDShadow(color: Theme.Color.red, offset: .zero, radius: 5.0, opacity: 0.2)
    }
    
    func hideLoading(){
        self.ref.endRefreshing()
        //        self.loadingView.hide(animated: true)
        
        self.hud.dismiss(animated : false)
    }
    
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        return true
    }
    
    
    @objc func onTouch() {
        self.view.endEditing(true)
    }
    
    
    //    func showAlert(title : String, message : String){
    //        let alert = UIAlertController.init(title: title, message: message, preferredStyle: .alert)
    //
    //        alert.setValue(Provider.getAttribute(text : title,font: Theme.Font.bold, color: Theme.Color.darkPrimary), forKey: "attributedTitle")
    //        alert.setValue(Provider.getAttribute(text : message,font: Theme.Font.regular, color: Theme.Color.darkPrimary), forKey: "attributedMessage")
    //
    //        alert.addAction(UIAlertAction(title: "موافق", style: .default, handler: nil))
    //
    //        self.present(alert, animated: true, completion: nil)
    //    }
    
    
}

