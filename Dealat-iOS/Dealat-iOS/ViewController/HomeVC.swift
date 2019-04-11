//
//  HomeVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/6/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit
import SideMenu
import CHIPageControl
import Crashlytics
import EggRating
import StoreKit

class HomeVC: BaseVC {
    
    @IBOutlet weak var tableView : UITableView!
    @IBOutlet weak var collectionView : UICollectionView!
    @IBOutlet weak var pageControl : CHIPageControlAji!
    
    var x1 = -1
    var timer : Timer = Timer()
    
    //    var commericals : [String]{
    //        var ar = [String]()
    //        ar.append("ad_image_39")
    //        ar.append("ad_image_40")
    //        ar.append("ad_image_42")
    //        ar.append("ad_image_43")
    //        return ar
    //    }
    var commericals : [Commercial] = [Commercial]()
    
    var cats  : [Cat] = [Cat]()
    /*{
     
     let c1 = Cat(JSON: ["category_name" : "Vehicles","mobile_image" : "Sc01 - cat"])
     
     c1?.children = [Cat(JSON: ["category_name" : "Hyundai"])!,
     Cat(JSON: ["category_name" : "BMW"])!,
     Cat(JSON: ["category_name" : "Audi"])!,
     Cat(JSON: ["category_name" : "Volkswagen"])!,
     Cat(JSON: ["category_name" : "Mercedes-Benz"])!
     ]
     
     let c2 = Cat(JSON: ["category_name" : "Real estate","mobile_image" : "real-estats"])
     
     let c3 = Cat(JSON: ["category_name" : "Mobile Phones","mobile_image" : "mobile"])
     
     let c4 = Cat(JSON: ["category_name" : "Furniture","mobile_image" : "furn"])
     
     let c5 = Cat(JSON: ["category_name" : "Kids","mobile_image" : "toy"])
     
     return [c1!,c2!,c3!,c4!,c5!]
     }*/
    
    var isChangeLanguage : Bool = false
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        if isChangeLanguage{
            let alert = UIAlertController.init(title: "ChangeLangTitle2".localized, message: "ChangeLangMessage2".localized, preferredStyle: .alert)
            alert.addAction(UIAlertAction.init(title: "OK".localized, style: .default, handler: nil))
            self.present(alert, animated: true, completion: nil)
        }
        
        getData()
        setupSideMenuNavController()
        
        Provider.setScreenName("HomeActivity")

    }
    
    override func setupViews() {
        self.tableView.delegate = self
        self.tableView.dataSource = self
        self.tableView.rowHeight = UITableView.automaticDimension
        self.tableView.estimatedRowHeight = 100
        self.tableView.addSubview(ref)
        
        collectionView.dataSource = self
        collectionView.delegate = self
        
        self.setupSearchBar()
        
        self.setupFilterButton()
        
        setupRate()
        
//        self.navigationItem.rightBarButtonItem = UIBarButtonItem.init(title: "Filter".localized, style: .plain, target: self, action: #selector(goToFilter))
        
    }
    
    
    func setupRate(){
        if let version = Bundle.main.infoDictionary?["CFBundleShortVersionString"] as? String {
            if let start_date = UserDefaults.standard.value(forKey: "start_date_\(version)") as? Date{
                
                if let _ = UserDefaults.standard.value(forKey: "is_rated_\(version)") as? Bool{
                }else if start_date.compare(with: Date(), only: Calendar.Component.day) >= 10{
                    UserDefaults.standard.set(true, forKey: "is_rated_\(version)")
                    
                    if #available(iOS 10.3, *) {
                        SKStoreReviewController.requestReview()
                    }else{
                        EggRating.promptRateUs(in: self)
                    }
                }
            }else{
                UserDefaults.standard.set(Date(), forKey: "start_date_\(version)")
            }
        }
    }
    
    func setupFilterButton(){
        let btn = UIButton.init()
        let image = #imageLiteral(resourceName: "filter-tool-black-shape").resized(toWidth: 15)?.withRenderingMode(.alwaysTemplate)
        btn.setImage(image, for: UIControl.State.normal)
        btn.setTitle("Filter".localized, for: UIControl.State.normal)
        btn.setTitleColor(Theme.Color.red, for: UIControl.State.normal)
        btn.titleLabel?.font = Theme.Font.Calibri.withSize(16)
        btn.contentEdgeInsets = UIEdgeInsets.init(top: 8, left: 8, bottom: 8, right: 8)
        btn.tintColor = Theme.Color.red
        btn.backgroundColor = .white
        btn.cornerRadius = 10
        if Provider.isArabic{
            btn.imageEdgeInsets.right = -8
            btn.transform = CGAffineTransform(scaleX: -1.0, y: 1.0)
            btn.titleLabel?.transform = CGAffineTransform(scaleX: -1.0, y: 1.0)
            btn.imageView?.transform = CGAffineTransform(scaleX: -1.0, y: 1.0)
        }else{
            btn.imageEdgeInsets.left = -8
        }
        btn.addTarget(self, action: #selector(self.goToFilter), for: UIControl.Event.touchUpInside)
        let barButton = UIBarButtonItem.init(customView: btn)
        self.navigationItem.rightBarButtonItem = barButton
    }
    
    @objc func goToFilter(){
        let vc = self.storyboard?.instantiateViewController(withIdentifier: "FilterBaseVC") as! FilterBaseVC
        vc.homeVC = self        
        Provider.filter = FilterParams()
        
        let nv = UINavigationController.init(rootViewController: vc)
        self.present(nv, animated: true, completion: nil)
    }
    
    override func getRefreshing() {
        Communication.shared.get_all { (response,categories,commercials) in
            self.hideLoading()
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    self.cats = categories
                    self.tableView.reloadData()
                    
                    self.commericals = commercials
                    
                    if commercials.isEmpty{
                        let im = UIImageView.init(image: #imageLiteral(resourceName: "add_images"))
                        im.contentMode = .scaleAspectFit
                        self.collectionView.backgroundView = im
                    }else{
                        self.collectionView.backgroundView = nil
                        self.pageControl.numberOfPages = self.commericals.count
                        self.timer.invalidate()
                        self.timer = Timer.scheduledTimer(timeInterval: 3, target: self, selector: #selector(self.rotate), userInfo: nil, repeats: true)
                        self.timer.fire()
                    }
                    self.collectionView.reloadData()
                    
                }else{
                    self.showAlertError(title: "ConnectionError".localized, message : value.message)
                }
                
            case .failure(let error):
                self.showAlertError(title: "ConnectionError".localized, message : error.localizedDescription)
            }
        }
    }
    
    func showAlertError( title : String, message : String){
        
        let alert = UIAlertController.init(title: title, message: message, preferredStyle: UIAlertController.Style.alert)
        
        alert.addAction(UIAlertAction.init(title: "TryAgain".localized, style: UIAlertAction.Style.default, handler: { (ac) in
            self.showLoading()
            self.getRefreshing()
        }))
        
        self.present(alert, animated: true, completion: nil)
    }

    
    func refreshTopCommercials(){
        /*Communication.shared.get_commercial_ads(0) { (res) in
            if let coms = res{
                self.hideLoading()
                self.commericals = coms
                
                self.pageControl.numberOfPages = self.commericals.count
                self.timer.invalidate()
                self.timer = Timer.scheduledTimer(timeInterval: 3, target: self, selector: #selector(self.rotate), userInfo: nil, repeats: true)
                self.timer.fire()
                self.collectionView.reloadData()
                
                if coms.isEmpty{
                    let im = UIImageView.init(image: #imageLiteral(resourceName: "add_images"))
                    im.contentMode = .scaleAspectFit
                    self.collectionView.backgroundView = im
                }
            }
        }*/
    }
    
    
    @IBAction func sellAction(){
        //TODO
        //        let vc = self.storyboard?.instantiateViewController(withIdentifier: "NewAddBaesVC") as! NewAddBaesVC
        //        self.navigationController?.pushViewController(vc, animated: true)
        
        if User.isRegistered(){
            let vc = self.storyboard?.instantiateViewController(withIdentifier: "NewAddBaesVC") as! NewAddBaesVC
            vc.homeVC = self
//            self.navigationController?.pushViewController(vc, animated: true)
            
            let nv = UINavigationController.init(rootViewController: vc)
            self.present(nv, animated: true, completion: nil)
        }else{
            let me = User.getCurrentUser()
            let txt = me.statues_key == (User.USER_STATUES.NEW_USER.rawValue) ? "needRegister1".localized : "needRegister2".localized
            
            let alert = UIAlertController.init(title: txt, message: nil, preferredStyle: .alert)
            
            alert.addAction(UIAlertAction.init(title: "OK".localized, style: .default, handler: { (ac) in
                AppDelegate.setupViews()
            }))
            
            alert.addAction(UIAlertAction.init(title: "Cancel".localized, style: .cancel, handler: nil))

            self.present(alert, animated: true, completion: nil)
//            self.showErrorMessage(text: "need_register".localized)
        }
        
        
        
        //        let vc = self.storyboard?.instantiateViewController(withIdentifier: "ChooseCatVC") as! ChooseCatVC
        //        vc.homeVC = self
        //
        //        vc.modalPresentationStyle = .overCurrentContext
        //        vc.modalTransitionStyle = .crossDissolve
        //        self.present(vc, animated: true, completion: nil)
    }
    
    
    @objc func rotate(){
        let cnt = self.commericals.count
        
        if(cnt > 1){
            if self.x1 != -1 {
                x1 = self.pageControl.currentPage + 1
            }else{
                self.x1 = 0
            }
        }
        else if (cnt == 1 || x1 > cnt - 1) {
            x1 = 0;
        }
        
        if cnt == 0{
            return 
        }
        
        
        let u = x1 % cnt
        
        self.collectionView.setContentOffset(CGPoint(x: self.collectionView.frame.size.width * CGFloat(u), y: 0) , animated: true)
    }
    
    func setupSideMenuNavController(){
        let menuController = self.storyboard?.instantiateViewController(withIdentifier: "SideMenuVC") as! SideMenuVC
        
        menuController.homeVC = self
        let menuLeftNavigationController = UISideMenuNavigationController(rootViewController: menuController)
        menuLeftNavigationController.isNavigationBarHidden = true
        
        if Provider.isArabic{
            menuLeftNavigationController.leftSide = false
            SideMenuManager.default.menuRightNavigationController = menuLeftNavigationController
        }else{
            menuLeftNavigationController.leftSide = true
            SideMenuManager.default.menuLeftNavigationController = menuLeftNavigationController
        }
        
        SideMenuManager.default.menuWidth = UIScreen.main.bounds.width * 2 / 3
        SideMenuManager.default.menuAllowPushOfSameClassTwice = true
        SideMenuManager.default.menuPresentMode = .menuSlideIn // YAHYA
        SideMenuManager.default.menuAnimationPresentDuration = 0.25 // YAHYA
        
        SideMenuManager.default.menuAddScreenEdgePanGesturesToPresent(toView: self.view)
        SideMenuManager.default.menuAnimationBackgroundColor = UIColor.clear
    }
    
    
    @IBAction func addSideMenuNavController()
    {
        if Provider.isArabic{
            present(SideMenuManager.default.menuRightNavigationController!, animated: true, completion: nil)
        }else{
            present(SideMenuManager.default.menuLeftNavigationController!, animated: true, completion: nil)
        }
    }
    
    override func searchBarSearchButtonClicked(_ searchBar: UISearchBar) {
        searchBar.resignFirstResponder()
        if let searchBarText = searchBar.text{
            let vc = self.storyboard?.instantiateViewController(withIdentifier: "AdsListVC") as! AdsListVC
//            vc.type = 1
            vc.query = searchBarText
//            Provider.searchText = searchBarText
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
}

extension HomeVC : UITableViewDelegate,UITableViewDataSource{
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.cats.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: _HomeCell, for: indexPath) as! HomeCell
        
        cell.tag = indexPath.row
        cell.cat = self.cats[indexPath.row]
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return UIScreen.main.bounds.width * 477 / 1128
    }
    
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        Provider.filter = FilterParams()

        for i in self.cats[indexPath.row].children{
            print("i.name \(i.category_name) - i.ads_count2 \(i.ads_count2)")
        }
        
        if self.cats[indexPath.row].children.count > 1{
            let vc = self.storyboard?.instantiateViewController(withIdentifier: "ChooseCatVC") as! ChooseCatVC
            vc.homeVC = self
            vc.cat = self.cats[indexPath.row]
            
            vc.modalPresentationStyle = .overCurrentContext
            vc.modalTransitionStyle = .crossDissolve
            self.present(vc, animated: true, completion: nil)
        }else{
            
            let vc = self.storyboard?.instantiateViewController(withIdentifier: "AdsListVC") as! AdsListVC
            vc.cat = self.cats[indexPath.row]
            self.navigationController?.pushViewController(vc, animated: true)
            
        }
        
    }
    
    
}


extension HomeVC : UICollectionViewDataSource, UICollectionViewDelegate,UICollectionViewDelegateFlowLayout{
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return self.commericals.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "cell", for: indexPath) as! CommericalCell
        //        cell.imageName = self.commericals[indexPath.row]
        cell.commercial = self.commericals[indexPath.row]
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize.init(width: collectionView.frame.width, height: collectionView.frame.height)
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        let commerical = self.commericals[indexPath.row]
        
        self.showLoading()
        Communication.shared.increment_clicks(commercial_ad_id: commerical.commercial_ad_id.stringValue) { (res) in
            self.hideLoading()
            self.openCommercial(commerical)
        }
        
    }
    
    func openCommercial(_ commerical : Commercial){
        guard let ad_url = commerical.ad_url else {
            return
        }
        if commerical.external.Boolean{
            if let url = URL.init(string: ad_url){
                if UIApplication.shared.canOpenURL(url){
                    UIApplication.shared.openURL(url)
                }
            }
        }else{
            print("EXTERNAL TO \(ad_url)")
            
        }
    }
    
    
    //Delegate With ScrollView (scroll item photos)
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if scrollView == self.collectionView{
            if self.commericals.count > 0 {
                if self.collectionView.visibleCells.count > 0{
                    let pp = round(self.collectionView.contentOffset.x / self.collectionView.frame.size.width)
                    self.pageControl.set(progress: Int(pp), animated: true)
                    
                    self.timer.invalidate()
                    self.timer = Timer.scheduledTimer(timeInterval: 3, target: self, selector: #selector(self.rotate), userInfo: nil, repeats: true)
                }
            }
        }
    }
}


