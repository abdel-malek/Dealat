//
//  BaseVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/6/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import Foundation
import UIKit
import JGProgressHUD
import KSToastView

class BaseVC: UIViewController,UITextFieldDelegate, UITextViewDelegate, UIGestureRecognizerDelegate,UISearchBarDelegate {
    
    var hud = JGProgressHUD.init(style: JGProgressHUDStyle.extraLight)
    var ref = UIRefreshControl()
    var searchBar:UISearchBar = UISearchBar(frame: CGRect.init(x : 0,y : 0,width : 200,height : 12))

    
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
    
    @objc func getNewMessage(_ not : Notification){
    

        print("getNewMessage")
        
        if self.restorationIdentifier != "ChatDetailsVC"{
            let n = UILocalNotification.init()
            n.userInfo = not.userInfo
            
            if let aps = not.userInfo!["aps"] as? [String: AnyObject]{
                if let msg = aps["alert"] as? [String: AnyObject]
                {
                    n.alertTitle = ((msg["title"] as? String) != nil) ? msg["title"] as! String : "Dealat"
                    n.alertBody = ((msg["body"] as? String) != nil) ? msg["body"] as! String : ""
                }
            }
            
            n.fireDate = Date()
            n.soundName = UILocalNotificationDefaultSoundName
            
            if !PushManager.LocalSchedules.contains(n.userInfo!["gcm.message_id"] as! String){
                PushManager.LocalSchedules.append(n.userInfo!["gcm.message_id"] as! String)
                UIApplication.shared.scheduleLocalNotification(n)
            }
        }
        
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
        
        notific.addObserver(self, selector: #selector(self.getNewMessage(_:)), name: "refreshChats".not, object: nil)

    }
    
    func unregisterReceivers()
    {
        notific.removeObserver(self, name: _ConnectionErrorNotification.not, object: nil)
        
        notific.removeObserver(self, name: _RequestErrorNotificationReceived.not, object: nil)
        
        notific.removeObserver(self, name: "refreshChats".not, object: nil)
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
        
        if text == "Not authorized"{
            User.clearMe()
            AppDelegate.setupViews()
        }
        
    }
    
    @objc func setupViews(){
        
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
        
//        navigationController?.navigationBar.barStyle = UIBarStyle.black;

        
        //setup back button
        self.navigationItem.hidesBackButton = false
        let rr = UIBarButtonItem(title: "", style: UIBarButtonItemStyle.plain, target: nil, action:nil)
        self.navigationItem.backBarButtonItem = rr
        //        }
        
    }
    
    func setupSearchBar(){
//        if #available(iOS 11.0, *) {
//            searchBar.heightAnchor.constraint(equalToConstant: 44).isActive = true
//        }
//        self.extendedLayoutIncludesOpaqueBars = true

//        self.searchBar.placeholder = "Search".localized
        self.searchBar.change(Theme.Font.Calibri)
        self.searchBar.tintColor = Theme.Color.red
        self.searchBar.delegate = self
//        self.navigationItem.titleView = searchBar
//        self.searchBar.sizeToFit()

        
        if #available(iOS 11.0, *) {
            let searchBarContainer = SearchBarContainerView(customSearchBar: self.searchBar)
            searchBarContainer.frame = CGRect(x: 0, y: 0, width: view.frame.width, height: 34)
            navigationItem.titleView = searchBarContainer
        }else{
            navigationItem.titleView = self.searchBar
        }

    }
    
//    override var preferredStatusBarStyle: UIStatusBarStyle{
//        return UIStatusBarStyle.lightContent
//    }
    
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
    
    func searchBarSearchButtonClicked(_ searchBar: UISearchBar) {
        searchBar.resignFirstResponder()

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


class SearchBarContainerView: UIView {
    
    let searchBar: UISearchBar
    
    init(customSearchBar: UISearchBar) {
        searchBar = customSearchBar
        super.init(frame: CGRect.zero)
        
        addSubview(searchBar)
        
//        searchBar.placeholder = "Search.Place.Holder".localized
    }
    
    override convenience init(frame: CGRect) {
        self.init(customSearchBar: UISearchBar())
        self.frame = frame
        
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func layoutSubviews() {
        super.layoutSubviews()
        searchBar.frame = bounds
        
    }
}



